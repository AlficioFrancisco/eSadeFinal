/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlpg;
import modelo.Funcao;
import modelo.Questaoautoavaliacao;
import conexao.Jpa;
import control.CategoriaJpaController;
import control.CursoJpaController;
import control.DepartamentoJpaController;
import control.EstudanteJpaController;
import control.FuncaoJpaController;
import control.GrupoJpaController;
import control.ParametroautoavaliacaoJpaController;
import control.ProfessorJpaController;
import control.QuestaoautoavaliacaoJpaController;
import control.RegimeJpaController;
import java.sql.Date;
import java.util.List;
import modelo.Categoria;
import modelo.Curso;
import modelo.Departamento;
import modelo.Estudante;
import modelo.Grupo;
import modelo.Parametroautoavaliacao;
import modelo.Professor;
import modelo.Regime;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.lang.Strings;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author ali_sualei
 */
public class parametroauto extends SelectorComposer<Component> {

    private List<Questaoautoavaliacao> listaquestaoautoavaliao;
    private List<Parametroautoavaliacao> listaParamAutoAval;
    private List<Grupo>listagrupo;
    private List<Estudante>listaestudante;
    private Parametroautoavaliacao selected;
    private List<Professor>listaprofessor;

    @Wire
    private Parametroautoavaliacao parametroautoavaliacao = new Parametroautoavaliacao();
    private ParametroautoavaliacaoJpaController pjc;
Grupo grupoo;
    @Wire
    private Window cadpauto;
////Machauma/////////////////////////////////////////////////////////////
    @Wire
    Button up, guardar, add;
    @Wire
    Listbox lista_autores, lista_autores2, lista_par, comboquest,combocurso,combocat,combodept,combofunc,comboreg;
    @Wire
    Div div_lista_autores,editgrupos;
    @Wire
    Textbox autor_search;
    @Wire
    Textbox codigo,grupo,observacaoAuto;
    @Wire
    Textbox descricao,nome,numeroestudante,email;
    
    @Wire
    Datebox ano;        
    ListModelList<Parametroautoavaliacao> lista2 = new ListModelList<Parametroautoavaliacao>();

    public Parametroautoavaliacao getParametroautoavaliacao() {
        return parametroautoavaliacao;
    }

    public void setParametroautoavaliacao(Parametroautoavaliacao parametroautoavaliacao) {
        this.parametroautoavaliacao = parametroautoavaliacao;
    }

    public void onAdd() {
        Messagebox.show("jgghhghg");
    }

//   
//    @NotifyChange({"parametroautoavaliacao"})
//    @Command
//    public void cadparametroautoavaliacao(){
//        parametroautoavaliacao=new Parametroautoavaliacao();
//    }
    @AfterCompose
    public void init(@ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        listaParamAutoAval = new ParametroautoavaliacaoJpaController(new Jpa().getEmf()).findParametroautoavaliacaoEntities();
        listaquestaoautoavaliao = new QuestaoautoavaliacaoJpaController(new Jpa().getEmf()).findQuestaoautoavaliacaoEntities();
    }

    @NotifyChange(("selected"))
    @Command
    public void cadpauto() {
        cadpauto.doModal();
        selected = new Parametroautoavaliacao();
    }
//
// @Command
//    public void gravaparametro() throws Exception{
//       new ParametroautoavaliacaoJpaController(new Jpa().getEmf()).create(parametroautoavaliacao);
//       Messagebox.show("parametro guardado....");
//       
//    }
    //////////////////////////////////////////////////////////////////////////////
      @Listen("onClick = #addobser")
    public void adicionarObservacao() {
        
                modelo.Autoavaliacao au = new modelo.Autoavaliacao();
               au.setAno(new Date(ano.getValue().getYear()));
               au.setObservacao(observacaoAuto.getText());
                  
                    
               try {
               new  control.AutoavaliacaoJpaController(new Jpa().getEmf()).create(au);
                    Clients.showNotification("Autoavaliado com sucesso!", "info", null, null, 3000);
                    
                    observacaoAuto.setValue("");
               } catch (Exception e) {
                   Clients.showNotification("erro ao guardar", "warning", null, null, 3000);
               }
                   
               

            }
   
      
    
    
    /////////////////////////////////////////////////////////////////////////////

    @Listen("onClick = #addquestao")
    public void adicionarParametro() {
        String codi = codigo.getValue();
        if (Strings.isBlank(codi)) {
            Clients.showNotification("Informe o codigo do parametro!", "warning", null, null, 3000);
        } else if (isCodigo(codi)) {
            Clients.showNotification("O codigo ja existe!", "warning", null, null, 3000);
        } else {
            Questaoautoavaliacao pau = new Questaoautoavaliacao();
            pau.setIdquestaoautoavaliacao(Short.MAX_VALUE);
            pau.setCodigo(codigo.getValue());
            pau.setDescricao(descricao.getValue());

            try {
                new QuestaoautoavaliacaoJpaController(new Jpa().getEmf()).create(pau);
                Clients.showNotification("Questao adicionado com sucesso!", "info", null, null, 3000);
                codigo.setValue("");
                descricao.setValue("");
            } catch (Exception ex) {
                Clients.showNotification("erro ao guardar", "warning", null, null, 3000);
            }
        }
    }
            
            
    @Listen("onClick = #addgrupo")
    public void adicionarGrupo() {
        String codi = grupo.getValue();
        if (Strings.isBlank(codi)) {
            Clients.showNotification("Informe o grupo de Utilizadores!", "warning", null, null, 3000);
        } else if (isGrupo(codi)) {
            Clients.showNotification("O grupo ja existe!", "warning", null, null, 3000);
        } else {
            Grupo gru = new Grupo();
            
            gru.setIdGrupo(grupo.getText());
            gru.setDescricao(descricao.getText());

            try {
                new GrupoJpaController(new Jpa().getEmf()).create(gru);
                Clients.showNotification("Grupo adicionado com sucesso!", "info", null, null, 3000);
                grupo.setValue("");
                descricao.setValue("");
            } catch (Exception ex) {
                Clients.showNotification("erro ao guardar", "warning", null, null, 3000);
            }
        }

        }

        
        @Listen("onClick = #addestuda")
    public void adicionarEstudante() {
        String codi = nome.getValue();
       if(Strings.isBlank(codi)){
          Clients.showNotification("Informe o nome do Estudante!", "warning", null, null, 3000);
       }
       else {
           if(isEstudante(codi))
               Clients.showNotification("O Estudante ja existe!", "warning", null, null, 3000);
           else{
                Estudante est =new Estudante();
               
               est.setNome(nome.getText());
               est.setNumeroestudante(numeroestudante.getText());
               est.setEmail(email.getText());
               est.setIdcurso((Curso) combocurso.getSelectedItem().getValue());    
                    
               try {
                    new EstudanteJpaController(new Jpa().getEmf()).create(est);
                    Clients.showNotification("Estudante adicionado com sucesso!", "info", null, null, 3000);
                    nome.setValue("");
                    numeroestudante.setValue("");
                    email.setValue("");
                    
               } catch (Exception e) {
                   Clients.showNotification("erro ao guardar", "warning", null, null, 3000);
               }
            
            }
        }
        
    }
    
          @Listen("onClick = #adddocente")
    public void adicionarProfess() {
        String codi = nome.getValue();
       if(Strings.isBlank(codi)){
          Clients.showNotification("Informe o nome do Estudante!", "warning", null, null, 3000);
       }
       else {
           if(isProfessor(codi))
               Clients.showNotification("O Estudante ja existe!", "warning", null, null, 3000);
           else{
                Professor pro =new Professor();
               
               pro.setNome(nome.getText());
               pro.setIdcategoria((Categoria) combocat.getSelectedItem().getValue());//.setNumeroestudante(numeroestudante.getText());
               pro.setIddepartamento((Departamento) combodept.getSelectedItem().getValue());
               pro.setIdfuncao((Funcao) combofunc.getSelectedItem().getValue());
               pro.setIdregime((Regime) comboreg.getSelectedItem().getValue());
                    
               try {
                    new ProfessorJpaController(new Jpa().getEmf()).create(pro);
                    Clients.showNotification("professor adicionado com sucesso!", "info", null, null, 3000);
                    nome.setValue("");
                   
                    
               } catch (Exception e) {
                   Clients.showNotification("erro ao guardar", "warning", null, null, 3000);
               }
            
            }
        }
        
    } 
    
    
  public boolean isEstudante(String codi) {
        listaestudante = new EstudanteJpaController(new Jpa().getEmf()).findEstudanteEntities();
        for (Estudante a : listaestudante) {
            if (a.getNome() != null) {
                if (a.getNome().equalsIgnoreCase(codi)) {
                    return true;
                }
            }
        }
        return false;
    }
   public boolean isProfessor(String codi) {
        listaprofessor = new ProfessorJpaController(new Jpa().getEmf()).findProfessorEntities();
        for (Professor a : listaprofessor) {
            if (a.getNome() != null) {
                if (a.getNome().equalsIgnoreCase(codi)) {
                    return true;
                }
            }
        }
        return false;
    }
  
    public boolean isCodigo(String codi) {
        listaquestaoautoavaliao = new QuestaoautoavaliacaoJpaController(new Jpa().getEmf()).findQuestaoautoavaliacaoEntities();
        for (Questaoautoavaliacao a : listaquestaoautoavaliao) {
            if (a.getCodigo() != null) {
                if (a.getCodigo().equalsIgnoreCase(codi)) {
                    return true;
                }
            }
        }
        return false;
    }
     public boolean isGrupo(String codi) {
       listagrupo = new GrupoJpaController(new Jpa().getEmf()).findGrupoEntities();
        for (Grupo a : listagrupo) {
            if (a.getIdGrupo() != null) {
                if (a.getIdGrupo().equalsIgnoreCase(codi)) {
                    return true;
                }
            }
        }
        return false;
    }
     @Listen("onClick = #editgrupo")
    public void doEditarGrupo() {
        if (Strings.isBlank(grupo.getValue()) || Strings.isBlank(descricao.getValue())) 
                 {
            Clients.showNotification("Preencha todos os campos", "warning", editgrupos, "before_center", 3000);
        } else {
            grupoo.setIdGrupo(grupo.getValue());//.setNome(nomeEV.getValue());
            grupoo.setDescricao(descricao.getValue());//.setMoradia(moradiaEV.getValue());
//            leitor.setEmail(emailEV.getValue());
//            leitor.setTelefone(telefoneEV.getValue());
            try {
                new GrupoJpaController(new Jpa().getEmf()).edit(grupoo);
                Clients.showNotification("grupo editado com sucesso!", "info", null, null, 3000);
                //preencherV(grupoo);
            } catch (Exception ex) {
                Clients.showNotification("Não foi possível editar o grupo!", "warning", null, null, 3000);
            }
        }
    }
// @Listen("onCheck = #funcionario; onCheck = #estudante")
//    public void place(){
//        if(estudante.isSelected())
//            text_pesquisa.setPlaceholder("Pesquisa por nome do estudante");
//        else
//            text_pesquisa.setPlaceholder("Pesquisa por nome do funcionario");
//    }
    @Listen("onCreate = #combo_curso; onCreate = #comboquest; onCreate = #combo_area")
    public void doCriarListas() {

        ListModelList<Questaoautoavaliacao> listaquest = new ListModelList<Questaoautoavaliacao>(new QuestaoautoavaliacaoJpaController(new Jpa().getEmf()).findQuestaoautoavaliacaoEntities());
        comboquest.setModel(listaquest);

    }
    
    @Listen("onCreate = #combocat; onCreate = #combodept; onCreate = #combofunc; onCreate = #comboreg")
    public void doCriarProfes() {

        ListModelList<Categoria> listacate = new ListModelList<Categoria>(new CategoriaJpaController(new Jpa().getEmf()).findCategoriaEntities());
        combocat.setModel(listacate);
         ListModelList<Departamento> listadepe = new ListModelList<Departamento>(new DepartamentoJpaController(new Jpa().getEmf()).findDepartamentoEntities());
        combodept.setModel(listadepe);
        
     ListModelList<Funcao> listafunc = new ListModelList<Funcao>(new FuncaoJpaController(new Jpa().getEmf()).findFuncaoEntities());
        combofunc.setModel(listafunc);
        
         ListModelList<Regime> listareg = new ListModelList<Regime>(new RegimeJpaController(new Jpa().getEmf()).findRegimeEntities());
        comboreg.setModel(listareg);
    }
    
    @Listen("onCreate = #combocurso")
    public void doComboCurso() {

        ListModelList<Curso> listcurso = new ListModelList<Curso>(new CursoJpaController(new Jpa().getEmf()).findCursoEntities());
        combocurso.setModel(listcurso);

    }

    public List<Grupo> getListagrupo() {
        return listagrupo;
    }

    public void setListagrupo(List<Grupo> listagrupo) {
        this.listagrupo = listagrupo;
    }

    public List<Estudante> getListaestudante() {
        return listaestudante;
    }

    public void setListaestudante(List<Estudante> listaestudante) {
        this.listaestudante = listaestudante;
    }

    public Parametroautoavaliacao getSelected() {
        return selected;
    }

    public void setSelected(Parametroautoavaliacao selected) {
        this.selected = selected;
    }

    public Grupo getGrupoo() {
        return grupoo;
    }

    public void setGrupoo(Grupo grupoo) {
        this.grupoo = grupoo;
    }

    public Button getUp() {
        return up;
    }

    public void setUp(Button up) {
        this.up = up;
    }

    public Button getGuardar() {
        return guardar;
    }

    public void setGuardar(Button guardar) {
        this.guardar = guardar;
    }

    public Button getAdd() {
        return add;
    }

    public void setAdd(Button add) {
        this.add = add;
    }

    public Listbox getLista_par() {
        return lista_par;
    }

    public void setLista_par(Listbox lista_par) {
        this.lista_par = lista_par;
    }

    public Listbox getComboquest() {
        return comboquest;
    }

    public void setComboquest(Listbox comboquest) {
        this.comboquest = comboquest;
    }

    public Listbox getCombocurso() {
        return combocurso;
    }

    public void setCombocurso(Listbox combocurso) {
        this.combocurso = combocurso;
    }

    public Listbox getCombocat() {
        return combocat;
    }

    public void setCombocat(Listbox combocat) {
        this.combocat = combocat;
    }

    public Listbox getCombodept() {
        return combodept;
    }

    public void setCombodept(Listbox combodept) {
        this.combodept = combodept;
    }

    public Listbox getCombofunc() {
        return combofunc;
    }

    public void setCombofunc(Listbox combofunc) {
        this.combofunc = combofunc;
    }

    public Listbox getComboreg() {
        return comboreg;
    }

    public void setComboreg(Listbox comboreg) {
        this.comboreg = comboreg;
    }

    public Div getEditgrupos() {
        return editgrupos;
    }

    public void setEditgrupos(Div editgrupos) {
        this.editgrupos = editgrupos;
    }

    public Textbox getGrupo() {
        return grupo;
    }

    public void setGrupo(Textbox grupo) {
        this.grupo = grupo;
    }

    public Textbox getObservacaoAuto() {
        return observacaoAuto;
    }

    public void setObservacaoAuto(Textbox observacaoAuto) {
        this.observacaoAuto = observacaoAuto;
    }

   

    public Textbox getNome() {
        return nome;
    }

    public void setNome(Textbox nome) {
        this.nome = nome;
    }

    public Textbox getNumeroestudante() {
        return numeroestudante;
    }

    public void setNumeroestudante(Textbox numeroestudante) {
        this.numeroestudante = numeroestudante;
    }

    public Textbox getEmail() {
        return email;
    }

    public void setEmail(Textbox email) {
        this.email = email;
    }

    public ParametroautoavaliacaoJpaController getPjc() {
        return pjc;
    }

    public void setPjc(ParametroautoavaliacaoJpaController pjc) {
        this.pjc = pjc;
    }

    public Window getCadpauto() {
        return cadpauto;
    }

    public void setCadpauto(Window cadpauto) {
        this.cadpauto = cadpauto;
    }

    public Listbox getLista_autores() {
        return lista_autores;
    }

    public void setLista_autores(Listbox lista_autores) {
        this.lista_autores = lista_autores;
    }

    public Listbox getLista_autores2() {
        return lista_autores2;
    }

    public void setLista_autores2(Listbox lista_autores2) {
        this.lista_autores2 = lista_autores2;
    }

    public Div getDiv_lista_autores() {
        return div_lista_autores;
    }

    public void setDiv_lista_autores(Div div_lista_autores) {
        this.div_lista_autores = div_lista_autores;
    }

    public Textbox getAutor_search() {
        return autor_search;
    }

    public void setAutor_search(Textbox autor_search) {
        this.autor_search = autor_search;
    }

    public Textbox getCodigo() {
        return codigo;
    }

    public void setCodigo(Textbox codigo) {
        this.codigo = codigo;
    }

    public Textbox getDescricao() {
        return descricao;
    }

    public void setDescricao(Textbox descricao) {
        this.descricao = descricao;
    }

    public ListModelList<Parametroautoavaliacao> getLista2() {
        return lista2;
    }

    public void setLista2(ListModelList<Parametroautoavaliacao> lista2) {
        this.lista2 = lista2;
    }

    public List<Parametroautoavaliacao> getListaParamAutoAval() {
        return listaParamAutoAval;
    }

    public void setListaParamAutoAval(List<Parametroautoavaliacao> listaParamAutoAval) {
        this.listaParamAutoAval = listaParamAutoAval;
    }

    public List<Questaoautoavaliacao> getListaquestaoautoavaliao() {
        return listaquestaoautoavaliao;
    }

    public void setListaquestaoautoavaliao(List<Questaoautoavaliacao> listaquestaoautoavaliao) {
        this.listaquestaoautoavaliao = listaquestaoautoavaliao;
    }

}
