package com.cmis.cooperative.service;

import com.cmis.cooperative.model.Product;
import com.cmis.cooperative.model.dataType.ProductStatus;
import com.cmis.cooperative.model.dto.LikeResponse;
import com.cmis.cooperative.model.dto.ProductRequestDto;
import com.cmis.cooperative.model.response.ProductDtoResponse;
import com.cmis.cooperative.model.vo.User;
import com.cmis.cooperative.repository.ProductRepository;
import com.cmis.cooperative.repository.UserRepository;
import com.cmis.cooperative.utils.ThrowError;
import com.cmis.cooperative.utils.converter.Converter;
import com.cmis.cooperative.utils.converter.ProductToProductDtoResponse;
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
    private final ProductToProductDtoResponse productToProductDtoResponse;
    private final Converter<Product, ProductDtoResponse> converter;

    @Override
    public ProductDtoResponse createProduct(ProductRequestDto productRequestDto, Authentication loggedInUser) {
        User user = validateUserUsername(loggedInUser.getName());
        Product foundProduct = productRepository.findByNameIgnoreCase(productRequestDto.name().trim()).orElse(null);
        if (foundProduct != null) {
            throwError.throwException(String.format("Product %s already exits", productRequestDto.name().trim()));
        }

        User owner = userRepository.findByPublicId(productRequestDto.ownerPublicId()).orElse(null);
        if (owner == null) {
            throwError.throwException("Missing product owner");
        }

        Product product = new Product();
        product.setName(productRequestDto.name().trim());
        product.setDescription(productRequestDto.description());
        product.setPrice(productRequestDto.price());
        product.setStatus(ProductStatus.NEW);
        product.setCreatedBy(user);
        product.setOwnedBY(owner);
        Product savedProduct = productRepository.save(product);

        ProductDtoResponse dto = converter.convert(savedProduct);

        return dto;
    }

    @Override
    public Page<ProductDtoResponse> getProducts(String searchParam,
                                                LocalDate startDate,
                                                LocalDate endDate,
                                                ProductStatus status,
                                                Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> root = cq.from(Product.class);

        List<Predicate> andPredicates = new ArrayList<>();
        andPredicates.add(cb.equal(root.get("deleted"), Boolean.FALSE));

        if (status != null) {
            andPredicates.add(cb.equal(root.get("status"), status));
        }

        if (startDate != null && endDate != null) {
            LocalDateTime startDateTime = startDate.atStartOfDay();
            LocalDateTime endDateTime = endDate.atTime(23, 59, 59);
            andPredicates.add(cb.between(root.get("dateRegistered"), startDateTime, endDateTime));
        }

        if (searchParam != null && searchParam.trim().length() >= 3) {
            andPredicates.add(cb.like(cb.upper(root.get("name")), "%" + searchParam.toUpperCase() + "%"));
        }

        cq.where(andPredicates.toArray(new Predicate[0]))
                .orderBy(cb.desc(root.get("id")));

        TypedQuery<Product> query = entityManager.createQuery(cq)
                .setMaxResults(pageable.getPageSize())
                .setFirstResult((int) pageable.getOffset());
        List<Product> products = query.getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Product> countRoot = countQuery.from(Product.class);

        List<Predicate> countPredicates = new ArrayList<>();
        countPredicates.add(cb.equal(countRoot.get("deleted"), Boolean.FALSE));

        if (status != null) {
            countPredicates.add(cb.equal(countRoot.get("status"), status));
        }

        if (startDate != null && endDate != null) {
            LocalDateTime startDateTime = startDate.atStartOfDay();
            LocalDateTime endDateTime = endDate.atTime(23, 59, 59);
            countPredicates.add(cb.between(countRoot.get("dateRegistered"), startDateTime, endDateTime));
        }

        if (searchParam != null && searchParam.trim().length() >= 3) {
            countPredicates.add(cb.like(cb.upper(countRoot.get("name")), "%" + searchParam.toUpperCase() + "%"));
        }

        countQuery.select(cb.countDistinct(countRoot));
        countQuery.where(countPredicates.toArray(new Predicate[0]));
        Long total = entityManager.createQuery(countQuery).getSingleResult();

        List<ProductDtoResponse> dtoList = products.stream()
                .map(converter::convert)
                .toList();

        return new PageImpl<>(dtoList, pageable, total);
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
