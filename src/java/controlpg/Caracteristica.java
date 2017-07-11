/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlpg;

import conexao.Jpa;
import control.CaracteristiquestaosuperiorJpaController;
import java.util.List;
import modelo.Caracteristiquestaosuperior;
import modelo.Parametroautoavaliacao;
import modelo.Parametrodocenteestudante;
import modelo.Questaodocentesuperior;
import org.zkoss.lang.Strings;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author Paulino Francisco
 */
public class Caracteristica extends SelectorComposer<Component> {
      private List<Caracteristiquestaosuperior> listaCaracteristiquestaosuperiors;
    private List<Questaodocentesuperior> listaqQuestaodocentesuperiors;
    
    @Wire
   private Questaodocentesuperior questaodocentesuperior =new Questaodocentesuperior() ;
   private Parametrodocenteestudante pde;
   
   @Wire
   private Window cadpauto;
////Machauma/////////////////////////////////////////////////////////////
    @Wire
    Button up, guardar, add;
    @Wire
    Listbox lista_autores, lista_autores2,lista_par,comboquest;
    @Wire
    Div div_lista_autores;
    @Wire
    Textbox autor_search;
    @Wire
    Textbox codigo;
    @Wire
    Textbox descricao;
    ListModelList<Parametroautoavaliacao> lista2 = new ListModelList<Parametroautoavaliacao>();
     
     
    @Listen("onClick = #addcaracte")
    public void adicionarParametro() {
        String codi = descricao.getValue();
       if(Strings.isBlank(codi)){
          Clients.showNotification("Informe o codigo do parametro!", "warning", null, null, 3000);
       }
       else {
           if(isCodigo(codi))
               Clients.showNotification("O codigo ja existe!", "warning", null, null, 3000);
           else{
                Caracteristiquestaosuperior casu = new Caracteristiquestaosuperior();
               casu.setIdcaracteristicasuperior(Short.MAX_VALUE);
               
               casu.setDescricao(descricao.getValue());
               
              
                  
               try {
                   new CaracteristiquestaosuperiorJpaController(new Jpa().getEmf()).create(casu);
                    Clients.showNotification("Caracteristica adicionada com sucesso!", "info", null, null, 3000);
                    
                    descricao.setValue("");
               } catch (Exception ex) {
                    Clients.showNotification("erro ao guardar", "warning", null, null, 3000);
               }
            }
        }
        
    }
    
     public boolean isCodigo(String codi){
         listaCaracteristiquestaosuperiors = new CaracteristiquestaosuperiorJpaController(new Jpa().getEmf()).findCaracteristiquestaosuperiorEntities();
        for (Caracteristiquestaosuperior a : listaCaracteristiquestaosuperiors) {
            if(a.getDescricao() != null){
                if(a.getDescricao().equalsIgnoreCase(codi))
                    return true;
            }
        }
        return false;
    } 
}
