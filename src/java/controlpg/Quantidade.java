/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlpg;

import conexao.Jpa;
import control.ParametroautoavaliacaoJpaController;
import java.awt.Event;
import modelo.Parametroautoavaliacao;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author ali_sualei
 */
public class Quantidade extends GenericForwardComposer<Component>{
     private Textbox quantidade;
     private Window self; //embedded object, the supervised window "mywin"
     private Page page; //the ZK zuml page
     private Label mylabel;
     
     private Parametroautoavaliacao pau= new Parametroautoavaliacao();
     
//     public void onChange$quantidade(Event event) {
//        pau.setQuantidade(quantidade.getValue());
//         try {
//             new ParametroautoavaliacaoJpaController(new Jpa().getEmf()).create(pau);
//       Clients.showNotification("erro ao guardar", "info", null, null, 3000);// mylabel.setValue( mytextbox.getValue());
//         } catch (Exception ex) {
//             
//             Clients.showNotification("erro ao guardar", "warning", null, null, 3000);
//         }
//         
//     }
    
}
