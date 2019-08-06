package ua.gladiator.libraryapp.model.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookDto {
    @NotEmpty
    private String name;

    @NotEmpty
    private String text;

    private String author;

    private String picUrl;

    @NotNull
    private Integer daysToReturn;

    @NotEmpty
    private List<String> attributes;
}