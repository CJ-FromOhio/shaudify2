package com.hezix.shaudifymain.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.N;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SongFilter {
    private String title;
}
