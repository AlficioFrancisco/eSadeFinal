/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlpg;

//import esira.controller.authentication.Grupo;
//import esira.controller.authentication.GrupoRole;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author user
 */
public class ProfileCreateUsersController extends GenericForwardComposer {

    

    private static final long serialVersionUID = 43014628867656917L;
    private Combobox cbgrupos, cbfunc, cbest, cbfac;
    private Listbox lbUser, lbgrupo;
    private Intbox tbanoing;
    private Label validation, pwd_val;
    private Textbox txtAccount, txtEmail, txtPass, txtPassConfirm, lid, txtnome, txtapelido, txtnrmec;
    private Radio rf, re, ne;
    Window mDialogAddUser, windowUser;
    Map<String, Object> par = new HashMap<String, Object>();
    String condfac = "", condnr = "", condgrupo = "", condnome = "", condgenero = "", condanoi = "", condano = "", condcurso = "";
    Textbox txProcurar;
    Combobox cbcurso, cbfaculdade, cbgrupop, cbcurso2;
    private Button btv;
 

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
       Messagebox.show("hjgghhjj");
    }

//   
//
//    public void onBtnvagrup() {
//        if (cbgrupos.getSelectedItem() == null) {
//            return;
//        }
//        Grupo g = (Grupo) cbgrupos.getSelectedItem().getValue();
//        if (lbgrupo.getListModel() == null) {
//            lbgrupo.setModel(new ListModelList(new ArrayList<Grupo>()));
//        }
//        if (!((ListModelList) lbgrupo.getListModel()).contains(g)) {
//            lbgrupo.setRows(lbgrupo.getItemCount() + 1);
//            ((ListModelList) lbgrupo.getListModel()).add(g);
//        }
//    }

//    public void onClick$saveUser() {
//       
//    }

    public void onEdit(ForwardEvent evt) throws Exception {
        Messagebox.show("hjgghhjj");
    }

    private void addFormConstraint() {
        
    }

    public void onDelete(final ForwardEvent evt) throws Exception {
        Messagebox.show("Apagar?", "Prompt", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
                new EventListener() {
                    @Override
                    public void onEvent(Event evet) {
                        switch (((Integer) evet.getData()).intValue()) {
                            case Messagebox.YES:
                                Button btn = (Button) evt.getOrigin().getTarget();
                                Listitem litem = (Listitem) btn.getParent().getParent();

                              
                            case Messagebox.NO:
                                return;
                        }
                    }

                });
    }

    private boolean validate() {
        if (!txtPass.getValue().equals(txtPassConfirm.getValue())) {
            return false;
        }
        return true;
    }

   
}
