package com.murtaza.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuoteApiResponseDto {

    private Integer id;
    private String quote;
    private String author;
}
