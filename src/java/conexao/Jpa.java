/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexao;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;


/**
 *
 * @author ali_sualei
 */
public class Jpa {
    

      @PersistenceUnit(unitName = "e_sadePU")
    private EntityManagerFactory emf = null;
      
    
    public EntityManagerFactory getEmf(){
        
        return emf = Persistence.createEntityManagerFactory("e_sadePU");
    }

}
