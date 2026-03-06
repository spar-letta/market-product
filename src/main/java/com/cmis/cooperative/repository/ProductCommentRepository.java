package com.cmis.cooperative.repository;

import com.cmis.cooperative.model.ProductComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductCommentRepository extends JpaRepository<ProductComment, Long> {
    Optional<ProductComment> findByPublicId(UUID commentParentPublicId);
}
