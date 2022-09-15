package com.example.bincard.model;

import androidx.annotation.NonNull;

import java.util.Collections;
import java.util.Comparator;

public class Bincard {
    private String id = "0";
    private String code = "";
    private String description = "";
    private String unit = "";
    private String buying = "";
    private String markup = "";
    private String selling = "";
    private String quantity = "";
    private String imageUrl = "";

    // Constructor
    public Bincard() {
    }
    public Bincard(String id, String code, String description,
                   String unit, String buying, String selling,
                   String markup, String imageUrl) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.unit = unit;
        this.buying = buying;
        this.selling = selling;
        this.markup = markup;
        this.imageUrl = imageUrl;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getBuying() {
        return buying;
    }
    public void setBuying(String buying) {
        this.buying = buying;
    }

    public String getMarkup() {
        return markup;
    }
    public void setMarkup(String imageUrl) {
        this.markup = imageUrl;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getItemCode() {
        return code;
    }
    public void setItemCode(String itemCode) {
        this.code = itemCode;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnit() {
        return unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSelling() {
        return selling;
    }
    public void setSelling(String selling) {
        this.selling = selling;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static Comparator<Bincard> bincardNameAZComparator = new Comparator<Bincard>() {
        @Override
        public int compare(Bincard bincard, Bincard bincard1) {
            return bincard.getDescription().compareTo(bincard1.getDescription());
        }
    };
    public static Comparator<Bincard> bincardNameZAComparator = new Comparator<Bincard>() {
        @Override
        public int compare(Bincard bincard, Bincard bincard1) {
            return bincard1.getDescription().compareTo(bincard.getDescription());
        }
    };


    public static Comparator<Bincard> bincardCodeAZComparator = new Comparator<Bincard>() {
        @Override
        public int compare(Bincard bincard, Bincard bincard1) {
            return bincard.getItemCode().compareTo(bincard1.getItemCode());
        }
    };
    public static Comparator<Bincard> bincardCodeZAComparator = new Comparator<Bincard>() {
        @Override
        public int compare(Bincard bincard, Bincard bincard1) {
            return bincard1.getItemCode().compareTo(bincard.getItemCode());
        }
    };


    public static Comparator<Bincard> bincardQuantityAZComparator = new Comparator<Bincard>() {
        @Override
        public int compare(Bincard bincard, Bincard bincard1) {
            return bincard.getQuantity().compareTo(bincard1.getQuantity());
        }
    };
    public static Comparator<Bincard> bincardQuantityZAComparator = new Comparator<Bincard>() {
        @Override
        public int compare(Bincard bincard, Bincard bincard1) {
            return bincard1.getQuantity().compareTo(bincard.getQuantity());
        }
    };


    @NonNull
    @Override
    public String toString() {
        return "Bincard{" +
                "id='" + id + '\'' +
                ", itemCode='" + code + '\'' +
                ", description='" + description + '\'' +
                ", unit='" + unit + '\'' +
                ", buying='" + buying + '\'' +
                ", selling='" + selling + '\'' +
                ", markup='" + markup + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
