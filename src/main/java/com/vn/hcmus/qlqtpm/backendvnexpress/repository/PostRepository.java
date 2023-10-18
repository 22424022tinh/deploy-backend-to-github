package com.vn.hcmus.qlqtpm.backendvnexpress.repository;

import com.vn.hcmus.qlqtpm.backendvnexpress.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

    @Query(
            value =
                    "SELECT p.*\n" +
                            "FROM category c\n" +
                            "JOIN post p ON c.category_id = p.category_id\n" +
                            "WHERE (\n" +
                            "    SELECT COUNT(*)\n" +
                            "    FROM post p2\n" +
                            "    WHERE p2.category_id = c.category_id AND p2.post_id <= p.post_id\n" +
                            ") <= 3\n" +
                            "ORDER BY c.category_id, p.post_id;\n" +
                            "\n", nativeQuery = true)
    List<PostEntity> findPostEntitiesByHome();
}
