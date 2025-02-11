package org.blogapplication.payloads;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {

    private Integer categoryId;

    @NotEmpty
    @Size(min = 4)
    private String categoryTitle;

    @NotEmpty
    @Size(min = 10)
    private String categoryDescription;
}
