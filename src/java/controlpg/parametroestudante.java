/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlpg;

import conexao.Jpa;
import control.ParametroautoavaliacaoJpaController;
import control.ParametrodocenteestudanteJpaController;
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
 * @author Paulino Francisco
 */
public class parametroestudante extends SelectorComposer<Component>{
      private List<Questaodocenteestudante> listaquestaoList;
    private List<Parametrodocenteestudante> listaParametrodocenteestudantes;
    
    @Wire
   private Parametroautoavaliacao parametroautoavaliacao =new Parametroautoavaliacao() ;
   private ParametroautoavaliacaoJpaController pjc;
   
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
    
   
    
        @Listen("onClick = #addparameto")
    public void adicionarParametro() {
        String codi = codigo.getValue();
       if(Strings.isBlank(codi)){
          Clients.showNotification("Informe o codigo do parametro!", "warning", null, null, 3000);
       }
       else {
           if(isCodigo(codi))
               Clients.showNotification("O codigo ja existe!", "warning", null, null, 3000);
           else{
                Parametrodocenteestudante pau = new Parametrodocenteestudante();
               
               pau.setCodigo(codigo.getText());
               pau.setDescricao(descricao.getText());
               pau.setIdquestaodocenteestudante((Questaodocenteestudante) comboquest.getSelectedItem().getValue());    
                    
               try {
                    new ParametrodocenteestudanteJpaController(new Jpa().getEmf()).create(pau);
                    Clients.showNotification("parameto adicionado com sucesso!", "info", null, null, 3000);
                    codigo.setValue("");
                    descricao.setValue("");
               } catch (Exception e) {
                   Clients.showNotification("erro ao guardar", "warning", null, null, 3000);
               }
            
            }
        }
        
    }
    
     public boolean isCodigo(String codi){
         listaParametrodocenteestudantes = new ParametrodocenteestudanteJpaController(new Jpa().getEmf()).findParametrodocenteestudanteEntities();
        for (Parametrodocenteestudante a : listaParametrodocenteestudantes) {
            if(a.getCodigo() != null){
                if(a.getCodigo().equalsIgnoreCase(codi))
                    return true;
            }
        }
        return false;
    }
     
      @Listen("onCreate = #combo_curso; onCreate = #comboquest; onCreate = #combo_area")
    public void doCriarListas() {
        
        ListModelList<Questaodocenteestudante> listaquest = new ListModelList<Questaodocenteestudante>(new QuestaodocenteestudanteJpaController(new Jpa().getEmf()).findQuestaodocenteestudanteEntities());
        comboquest.setModel(listaquest);
        
    }
}
