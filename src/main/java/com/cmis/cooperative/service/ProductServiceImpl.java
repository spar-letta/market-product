package com.cmis.cooperative.service;

import com.cmis.cooperative.model.Product;
import com.cmis.cooperative.model.dataType.ProductStatus;
import com.cmis.cooperative.model.dto.LikeResponse;
import com.cmis.cooperative.model.dto.ProductRequestDto;
import com.cmis.cooperative.model.vo.User;
import com.cmis.cooperative.repository.ProductRepository;
import com.cmis.cooperative.repository.UserRepository;
import com.cmis.cooperative.utils.ThrowError;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    @PersistenceContext
    private EntityManager entityManager;

    private final ProductRepository productRepository;
    private final ThrowError throwError;
    private final UserRepository userRepository;

    @Override
    public Product createProduct(ProductRequestDto productRequestDto, Authentication loggedInUser) {
        User user = validateUserUsername(loggedInUser.getName());
        Product foundProduct = productRepository.findByNameIgnoreCase(productRequestDto.name().trim()).orElse(null);
        if (foundProduct != null) {
            throwError.throwException(String.format("Product %s already exits", productRequestDto.name().trim()));
        }

        Product product = new Product();
        product.setName(productRequestDto.name().trim());
        product.setDescription(productRequestDto.description());
        product.setPrice(productRequestDto.price());
        product.setStatus(ProductStatus.NEW);
        product.setCreatedBy(user);

        return productRepository.save(product);
    }

    @Override
    public Page<Product> getProducts(String searchParam, LocalDate startDate, LocalDate endDate, ProductStatus status, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> root = cq.from(Product.class);

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);

        cq.distinct(true);

        final List<Predicate> andPredicates = new ArrayList<>();

        Predicate deletedPredicate = cb.equal(root.get("deleted"), Boolean.FALSE);
        andPredicates.add(deletedPredicate);

        if (status != null) {
            Predicate newPredicate = cb.equal(root.get("status"), status);
            andPredicates.add(newPredicate);
        }

        if (startDate != null && endDate != null) {
            LocalDateTime startDateTime = startDate.atStartOfDay();
            LocalDateTime endDateTime = endDate.atStartOfDay();
            Predicate newPredicate = cb.between(root.get("dateRegistered"), startDateTime, endDateTime);
            andPredicates.add(newPredicate);
        }

        if (searchParam != null && searchParam.trim().length() >= 3) {
            final List<Predicate> orPredicates = new ArrayList<>();
            orPredicates.add(cb.like(cb.upper(root.get("name")), "%" + searchParam.toUpperCase() + "%"));
            Predicate p = cb.or(orPredicates.toArray(new Predicate[orPredicates.size()]));
            andPredicates.add(p);
        }

        cq.where(andPredicates.toArray(new Predicate[andPredicates.size()])).orderBy(cb.desc(root.get("id")));

        TypedQuery<Product> query = entityManager.createQuery(cq).setMaxResults(pageable.getPageSize()).setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        List<Product> queryResultList = query.getResultList();

        return new PageImpl<>(queryResultList, pageable, queryResultList.size());
    }

    @Override
    public Product updateProduct(UUID publicId, ProductRequestDto productRequestDto, Authentication loggedInUser) {
        User user = validateUserUsername(loggedInUser.getName());
        Product product = getProduct(publicId, loggedInUser);

        if (!product.getName().trim().equalsIgnoreCase(productRequestDto.name().trim())) {
            Product foundProduct = productRepository.findByNameIgnoreCase(productRequestDto.name().trim()).orElse(null);
            if (foundProduct != null) {
                throwError.throwException(String.format("Product %s already exits", productRequestDto.name().trim()));
            }
        }

        product.setName(productRequestDto.name().trim());
        product.setDescription(productRequestDto.description());
        product.setPrice(productRequestDto.price());
        product.setModifiedBy(user);

        return productRepository.save(product);
    }

    @Override
    public Product getProduct(UUID publicId, Authentication loggedInUser) {
        Product foundProduct = productRepository.findByPublicId(publicId).orElse(null);
        if (foundProduct == null) {
            throwError.throwException(String.format("Product does not exit"));
        }

        return foundProduct;
    }

    @Override
    public void deleteProduct(UUID publicId, Authentication loggedInUser) {
        User user = validateUserUsername(loggedInUser.getName());
        Product product = getProduct(publicId, loggedInUser);
        product.setDeleted(true);
        product.setModifiedBy(user);
        productRepository.save(product);
    }

    @Override
    public void makeLike(UUID productPublicId, Authentication loggedInUser) {
        User user = validateUserUsername(loggedInUser.getName());
        Product product = getProduct(productPublicId, loggedInUser);

        if (!product.getUserLikes().isEmpty()) {
            Set<User> likes = product.getUserLikes();
            if (!likes.contains(user)) {
                likes.add(user);
            } else {
                likes.remove(user);
            }
        } else {
            product.getUserLikes().add(user);
        }
        productRepository.save(product);
    }

    @Override
    public LikeResponse getProductLike(UUID publicId, Authentication loggedInUser) {
        User user = validateUserUsername(loggedInUser.getName());
        Product product = getProduct(publicId, loggedInUser);

        Set<User> userLikes = product.getUserLikes();
        long numberOfLikes = userLikes.size();
        boolean liked = userLikes.contains(user);

        LikeResponse likeResponse = new LikeResponse(liked, numberOfLikes);

        return likeResponse;
    }

    public User validateUserUsername(String username) {
        User user = userRepository.findByUserName(username).orElse(null);
        if (user == null) {
            throwError.throwException(String.format("User does not exit"));
        }
        return user;
    }
}
