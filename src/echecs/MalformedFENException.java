/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echecs;

import javax.swing.JOptionPane;

/**
 *
 * @author Acevedo Roman and Guillemot Baptiste, based on Delepoulle Samuel's
 *         code.
 */
@SuppressWarnings("serial")
public class MalformedFENException extends IllegalArgumentException {

	MalformedFENException() {
		super();
	}

	MalformedFENException(String msg) {
		super(msg);
		JOptionPane.showMessageDialog(null, msg);
	}

}