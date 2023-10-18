package com.vn.hcmus.qlqtpm.backendvnexpress.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "CATEGORY")
@Table(name = "CATEGORY")
@Builder
public class CategoryEntity {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categoryEntity")
    List<PostEntity> postEntityList;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_ID")
    private Long categoryId;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "SLUG")
    private String slug;
    @Column(name = "CREATED_BY")
    private Long createdBy;
    @Column(name = "UPDATED_BY")
    private Long updatedBy;
    @CreationTimestamp
    @Column(name = "CREATED_AT")
    private Date createdAt;
    @UpdateTimestamp
    @Column(name = "UPDATED_AT")
    private Date updatedAt;
}
