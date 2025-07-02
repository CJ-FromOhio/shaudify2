package com.hezix.shaudifymain.filter;

import java.io.Serializable;

public record UserFilter(String firstName,
                         String lastName) implements Serializable {
}
