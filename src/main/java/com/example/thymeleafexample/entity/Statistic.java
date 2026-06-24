package com.example.thymeleafexample.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class Statistic {
    private List<Object> labels = new ArrayList<>();
    private List<Long> values = new ArrayList<>();
}
