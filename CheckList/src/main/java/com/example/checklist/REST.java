/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.checklist;

import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.security.enterprise.identitystore.IdentityStoreHandler;
import javax.security.enterprise.identitystore.PasswordHash;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import lombok.extern.java.Log;

/**
 *
 * @author Stigus
 */
@Path("REST")
@Stateless
@Log
public class REST {
    
    @Inject
    AuthentictionService authService;
    
    @Inject
    IdentityStoreHandler identityStoreHandler;
    
    @Context
    SecurityContext sc;

    @PersistenceContext
    EntityManager em;

    @Inject
    PasswordHash hasher;
    
    @POST
    @Path("addTemplate")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({Group.USER})
    public Response addTemplate(@FormParam("title") String title){
        
        
        CheckList checklist = new CheckList();
        checklist.setTitle(title);
        checklist.setTemplate(true);
        
        
        em.merge(checklist);
        return Response.ok(checklist).build(); 
    }
    
    @POST
    @Path("addItemsToTemplate")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({Group.USER})
    public Response addItemsToTemplate(@FormParam("checklistid") Long checklistid,
            @FormParam("title") String title){
        
        Item item = new Item();
        CheckList checklist = getCheckList(checklistid);
        
        item.setChecklist(checklist);
        item.setTitle(title);
        item.setChecked(false);
        
        
        em.merge(item);
        checklist.addItem(item);
        return Response.ok(item).build();
    }
    

            
    /**
     * TODO: Check if user is part of receivers
     *
     * @param conversationid
     * @return
     */
    private CheckList getCheckList(Long checklistid) {
        return em.createNamedQuery(CheckList.FIND_BY_ID,CheckList.class)
                 .setParameter("checklistid", checklistid)
                 .getSingleResult();
    }
    
    @GET
    @Path("templates")
    public List<CheckList> getTemplates() {
        return em.createNamedQuery(CheckList.FIND_ALL_TEMPLATES, CheckList.class).getResultList();        
    }
    
    @GET
    @Path("chekclists")
    public List<CheckList> getCheckLists(){
        return em.createNamedQuery(CheckList.FIND_ALL_CHECKLISTS, CheckList.class).getResultList();  
    }
    
    
    
  //  @GET
   // @Path("checklist/{checklistid}")
   // @RolesAllowed({Group.USER})
   // public List<
    
    /**Checklist
     User owner
     List<item> items
     STring title
     
     Item
     * checklist checklist;
     * STring name
     * 
     * 
     * @Entity
     * Class Item
     * STring title
     * boolean done
     * public item(item copy)
     * this.title = copy.title;
     * 
     * Item item
     * items.add(item)
     * item.setChecklist(this);
     * 
     * public Checklist(checklist temp)
     * this.title = temp.title;
     * temp.getItems().forEach(i ->)
     * add(new Item(i))
     * 
     */
                             
                             
    
}



