//
//  specifiche :
//   definite le classi atomiche
//   sbs_code_name
//          "stringa" +
//          'valore'  +
//          una flag che dice se il valore (inizializzato a 0) ha un valore sensato
//   (e.g.  "TDHBF"-'35'-true  "TDPC"-'1'-true   "ysmsh_sh_objlist_actreq"-'0'-false)
//
//   sbs_processor
//      sbs_code_name
//
//
//  1-   aggiungere 1 interfaccia : DHBF
//             variabili : 1. nome utente interfaccia  "DHBF"
//                         2. nome sbs interfaccia "TDHBF"
//                         3. valore sbs interfaccia '35'
//                         4. processore
//                                 classe sbs_processore con
//                                     1) nome sbs processore "TDPC"
//                                     2) valore sbs_processore '1'
//  2-   aggiungere 1 msg tra  2 interfacce   DHGP ---->  DHBF

package  sbs;


public class sbs_codeName {
    private  String  name;
    private  int     name_id;
    private  boolean valid_name_id;
}

