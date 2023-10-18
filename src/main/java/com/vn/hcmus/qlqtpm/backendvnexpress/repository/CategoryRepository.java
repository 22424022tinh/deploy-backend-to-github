package com.vn.hcmus.qlqtpm.backendvnexpress.repository;

import com.vn.hcmus.qlqtpm.backendvnexpress.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    @Query(
            value =
                    "SELECT c.*\n" +
                            "FROM category c\n" +
                            "JOIN post p ON c.category_id = p.category_id\n" +
                            "WHERE (\n" +
                            "    SELECT COUNT(*)\n" +
                            "    FROM post p2\n" +
                            "    WHERE p2.category_id = c.category_id AND p2.post_id <= p.post_id\n" +
                            ") <= 3\n" +
                            "ORDER BY c.category_id, p.post_id;\n" +
                            "\n", nativeQuery = true)
    List<CategoryEntity> findCategoryEntitiesByHome();

    Optional<CategoryEntity> findCategoryEntityByCategoryId(Long categoryId);

}
