/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlpg;

import conexao.Jpa;
import control.QuestaodocenteestudanteJpaController;
import java.util.List;
import modelo.Parametroautoavaliacao;
import modelo.Parametrodocenteestudante;
import modelo.Questaodocenteestudante;
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
 * @author ali_sualei
 */
public class Avaliacaoestudante extends SelectorComposer<Component>{
    
    private List<Questaodocenteestudante> listaquestaodocenteestudante;
    private List<Parametrodocenteestudante> listaParametrodocenteestudantes;
    
    @Wire
   private Parametrodocenteestudante parametrodocenteestudante =new Parametrodocenteestudante() ;
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
     
     
    @Listen("onClick = #addquestao")
    public void adicionarParametro() {
        String codi = codigo.getValue();
       if(Strings.isBlank(codi)){
          Clients.showNotification("Informe o codigo do parametro!", "warning", null, null, 3000);
       }
       else {
           if(isCodigo(codi))
               Clients.showNotification("O codigo ja existe!", "warning", null, null, 3000);
           else{
                Questaodocenteestudante pau = new Questaodocenteestudante();
               pau.setIdquestaodocenteestudante(Short.MAX_VALUE);
               pau.setCodigo(codigo.getValue());
               pau.setDescricao(descricao.getValue());
               
              
                  
               try {
                   new QuestaodocenteestudanteJpaController(new Jpa().getEmf()).create(pau);
                    Clients.showNotification("Questao adicionado com sucesso!", "info", null, null, 3000);
                    codigo.setValue("");
                    descricao.setValue("");
               } catch (Exception ex) {
                    Clients.showNotification("erro ao guardar", "warning", null, null, 3000);
               }
            }
        }
        
    }
    
     public boolean isCodigo(String codi){
         listaquestaodocenteestudante = new QuestaodocenteestudanteJpaController(new Jpa().getEmf()).findQuestaodocenteestudanteEntities();
        for (Questaodocenteestudante a : listaquestaodocenteestudante) {
            if(a.getCodigo() != null){
                if(a.getCodigo().equalsIgnoreCase(codi))
                    return true;
            }
        }
        return false;
    }
}
