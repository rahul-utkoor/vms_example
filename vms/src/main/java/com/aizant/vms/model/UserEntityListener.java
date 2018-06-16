package com.aizant.vms.model;

import static com.aizant.vms.model.Action.DELETED;
import static com.aizant.vms.model.Action.INSERTED;
import static com.aizant.vms.model.Action.UPDATED;
import static javax.transaction.Transactional.TxType.MANDATORY;

import javax.persistence.EntityManager;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.transaction.Transactional;

import com.aizant.vms.service.BeanUtil;

/**
 * @author Naresh 
 */

public class UserEntityListener {

	
	
    @PrePersist
    public void prePersist(User target) {
        perform(target, INSERTED);
    }

    @PreUpdate
    public void preUpdate(User target) {
        perform(target, UPDATED);
    }

    @PreRemove
    public void preRemove(User target) {
        perform(target, DELETED);
    }

    @Transactional(MANDATORY)
    private void perform(User target, Action action) {
    	 EntityManager entityManager = BeanUtil.getBean(EntityManager.class);
    	 entityManager.persist(new UserHistory(target, action));
    }

}
