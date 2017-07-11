/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicos;

/**
 *
 * @author Almerindo Uazela
 */
public interface Autenticacao {
    /**login with account and password
     * @param account
     * @param password
     * @return */
	public int login(String account, String password);
	
	/**logout current user**/
	public void logout();
	
	/**get current user credential
     * @return l**/
	public UserCredential getUserCredential();
	    
}
