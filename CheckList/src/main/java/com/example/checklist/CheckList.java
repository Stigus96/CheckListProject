/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.checklist;

import static com.example.checklist.CheckList.FIND_ALL_CHECKLISTS;
import static com.example.checklist.CheckList.FIND_ALL_TEMPLATES;
import static com.example.checklist.CheckList.FIND_BY_ID;
import static com.example.checklist.CheckList.FIND_BY_OWNER;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author Stigus
 * A checklist to be used by a user
 */
@Entity @Table(name = "ACHECKLIST")@EqualsAndHashCode
@Data @AllArgsConstructor @NoArgsConstructor
@NamedQuery(name = FIND_ALL_TEMPLATES, query = "SELECT c FROM CheckList c WHERE c.template = true")
@NamedQuery(name = FIND_ALL_CHECKLISTS, query = "SELECT c FROM CheckList c")
@NamedQuery(name = FIND_BY_ID, query = "SELECT c FROM CheckList c WHERE c.checklistid = :checklistid")
@NamedQuery(name = FIND_BY_OWNER, query = "SELECT c FROM CheckList c WHERE c.owner.userid = :owner")

public class CheckList implements Serializable {
    public static final String FIND_ALL_TEMPLATES = "CheckList.findAllTemplates";
    public static final String FIND_ALL_CHECKLISTS = "CheckList.findAllChecklists";
    public static final String FIND_BY_ID = "CheckList.findById";
    public static final String FIND_BY_OWNER = "Checklist.findByUser";
    
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue
    Long checklistid;
    
    @NotBlank(message = "Title of checklist cannot be blank")
    String title;
    
    @ManyToOne(optional = true, cascade = CascadeType.PERSIST)
    User owner;
    
    boolean template;
    
    @OneToMany(mappedBy = "checklist",cascade = CascadeType.ALL)
    List<Item> items;
    
    public void addItem(Item item){
        if(this.items == null){
            this.items = new ArrayList<>();
        }
        this.items.add(item);
    }
    

    
 
    
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "achecklist_properties", joinColumns=@JoinColumn(name="checklistid"))
    @MapKeyColumn(name="key")
    @Column(name = "value")
    Map<String,String> properties = new HashMap<>();
    
}
