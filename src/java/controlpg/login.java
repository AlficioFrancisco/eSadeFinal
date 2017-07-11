/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlpg;

import conexao.Jpa;
import control.ProfessorJpaController;
import control.UsersJpaController;
import java.util.List;
import modelo.Professor;
import modelo.Users;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import servicos.Autenticacao;
import servicos.AutenticacaoImpl;

/**
 *
 * @author ali_sualei
 */
public class login extends SelectorComposer<Component> {

    private static final long serialVersionUID = 1L;
private List<Professor> utilicorente ;//=new Professor();
public Professor prof,prof1;
    //wire components
    @Wire
    Textbox account;
    @Wire
    Textbox password;
    @Wire
    Label message;

    //services
    Autenticacao authService = new AutenticacaoImpl();

    @Listen("onClick=#login; onOK=#loginWin")
    public void doLogin() {
        String nm = account.getValue();
        String pd = password.getValue();
        List<Users> users = new UsersJpaController(new Jpa().getEmf()).findUsersEntities();
        for (Users user : users) {
            if (user.getUtilizador().equals(nm) && user.getPasword().equals(pd) && user.getIdGrupo().getIdGrupo().equals("recursos humanus")){ 
                prof= user.getIdprofessor();
                //
                Executions.sendRedirect("/listaParametroAuto.zul");
                return;
            }
            else
            if (user.getUtilizador().equals(nm) && user.getPasword().equals(pd) && user.getIdGrupo().getIdGrupo().equals("superior hierarquico")){ 
              prof=user.getIdprofessor();
                Executions.sendRedirect("/superiorh1.zul");
                return;
            }
                else
            if (user.getUtilizador().equals(nm) && user.getPasword().equals(pd) && user.getIdGrupo().getIdGrupo().equals("docente")){ 
               prof=user.getIdprofessor();
                Executions.sendRedirect("/autoavaliacao2.zul");
                return;
            }
                else
            if (user.getUtilizador().equals(nm) && user.getPasword().equals(pd) && user.getIdGrupo().getIdGrupo().equals("Estudante")) {
                prof=user.getIdprofessor();
                Executions.sendRedirect("/avaliacao2.zul");
                return;
            }
//            
             
            

        }
//       
    }
    
@AfterCompose
    public void init(@ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        
        prof1= prof;
        
    }

    public Professor getProf1() {
        return prof1;
    }

    public void setProf1(Professor prof1) {
        this.prof1 = prof1;
    }

    public List<Professor> getUtilicorente() {
        return utilicorente;
    }

    public void setUtilicorente(List<Professor> utilicorente) {
        this.utilicorente = utilicorente;
    }
    
    public Professor getProf() {
        return prof;
    }

    public void setProf(Professor prof) {
        this.prof = prof;
    }

    public Textbox getAccount() {
        return account;
    }

    public void setAccount(Textbox account) {
        this.account = account;
    }

    public Textbox getPassword() {
        return password;
    }

    public void setPassword(Textbox password) {
        this.password = password;
    }

    public Label getMessage() {
        return message;
    }

    public void setMessage(Label message) {
        this.message = message;
    }

    public Autenticacao getAuthService() {
        return authService;
    }

    public void setAuthService(Autenticacao authService) {
        this.authService = authService;
    }
    
    
    
}
