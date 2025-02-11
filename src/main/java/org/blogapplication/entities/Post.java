package org.blogapplication.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Role;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;

    @Column(length = 255,nullable = false)
    private String title;
    private String imageName;

    @Column(length = 10000)
    private String content;
    private Date addedDate;

    @ManyToOne
    @JsonBackReference
    private Category category;

    @ManyToOne
    @JsonIgnoreProperties("posts")
    private User user;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private Set<Comment> comments=new HashSet<>();
}
