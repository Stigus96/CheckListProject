/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.checklist;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author Stigus
 * An item to be used in a checklist
 */
@Entity @Table(name = "AITEM") @EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data @AllArgsConstructor @NoArgsConstructor
public class Item implements Serializable {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue
    Long itemid;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    Date created;
    
    @JsonbTransient
    @ManyToOne(optional = false,cascade = CascadeType.PERSIST)
    CheckList checklist;
    
    @NotBlank(message = "Title cannot be blank")
    String title;
    
    boolean checked;
    
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "aitem_properties", joinColumns=@JoinColumn(name="itemid"))
    @MapKeyColumn(name="key")
    @Column(name = "value")
    Map<String,String> properties = new HashMap<>();
    
    public Item (Item item){
        this.title = item.title;
        this.checklist = item.checklist;
    }
    
    @PrePersist
    protected void onCreate() {
        created = new Date();
    }
    
    
}
