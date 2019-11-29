package codigoaula16mysql;

// Versão 5.1
//import com.mysql.jdbc.Driver;

// Versão 8
import com.mysql.cj.jdbc.Driver; //com jdbc 8 
import java.io.InputStream;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class CodigoAula16MySQL {

      
    public static void main(String[] args) {
    
    Scanner sc = new Scanner(System.in);
    boolean running = true;
    
    while(running){
	
    System.out.println("\n===========================================================");	
    System.out.println("====================   PROGRAMINHA CRUD  ====================");	
    System.out.println("=============================================================");
    
    
    System.out.println("[1] Cadastro de Usuário: ");
    System.out.println("[2] Listar Usuários Cadastrados: ");  
    System.out.println("[3] Editar Usuários: ");
    System.out.println("[4] Deletar Usuário: ");
    System.out.println("[5] Fechar Programa: ");
    System.out.print("\nEscolha Uma Opção: ");
    String opcao = sc.nextLine();
    
    switch(opcao){
	
	case "1":{
	    
	  
	System.out.println("\n=======================");
	System.out.println("===|  NOVO CADASTRO  |===");
	System.out.println("=========================");
	
	System.out.print("Digite seu nome: ");
	String nome = sc.nextLine();
	System.out.print("Digite seu email: ");
	String email = sc.nextLine();
	System.out.print("Digite sua senha: ");
	String senha = sc.nextLine();
	System.out.print("Digite novamente sua senha: ");
	String confirmaSenha = sc.nextLine();
	
	Usuario user = new Usuario();
	user.setNome(nome);
	user.setEmail(email);
	user.setSenha(senha);
	user.setConfirmaSenha(confirmaSenha);
	
	if(user.getSenha().equals(user.getConfirmaSenha())){
	   
	    
	    inserirUsuario(user);  
	    
	    System.out.println("Usuário Cadastrado com sucesso!");
          
	    
	}else{
	    System.out.println("Algo deu ERRADO. Tente Novamente!");
	}   break;
	
	
	
	}
	
	
	case "2":{
	
	System.out.println("\n");
	System.out.println("===================================================");
	System.out.println("===|   LEITURA DO BANCO DE DADOS  |===");
	System.out.println("===================================================");
        ArrayList<Usuario> usuarios = buscarUsuarios();
         
        for(int i = 0; i < usuarios.size(); i++) {
            Usuario uTemp = usuarios.get(i);
		    
            System.out.println("Usuário " + i);
            System.out.println("\tId: " + uTemp.getId());
	    System.out.println("\tNome: " + uTemp.getNome());
            System.out.println("\tEmail: " + uTemp.getEmail());
            System.out.println("\tSenha: " + uTemp.getSenha());
           
      	}break;
    }	
    
	
    case "3":{
	
	System.out.println("\n");
	System.out.println("===================================================");
	System.out.println("=====|       BUSCAR USUÁRIO ESPECÍFICO      |======");
	System.out.println("===================================================");
	
	System.out.println("Digite o email do Usuário que você que editar: ");
	String email = sc.nextLine();
	ArrayList<Usuario> usuarios = buscarUsuarios();
	
	for (int i = 0; i < usuarios.size(); i++){
	Usuario user = usuarios.get(i);  
	
	if (user.getEmail().equals(email)) {   //ESSA PORRA NÃO FUNFA

			    System.out.println("\n-------------------------------------------------> ID:  " + user.getId() + "\n-------------------------------------------------> NOME:  " + user.getNome() + "\n-------------------------------------------------> EMAIL: " + user.getEmail() + "\n-------------------------------------------------> SENHA: " + user.getSenha() + "");
			    
			  
			   
			            atualizarUsuario(user);
			    
			    break;
			    // i percorre o Array todo i = indices 0,1,2 ... o -1 é para para no ultimo indice ... pq conta os elementos    
			} else if (i == usuarios.size() - 1) {
			    System.out.println("\n-------------------------------------------------> USUÁRIO NÃO CADASTRADO!");
			}
		    }
		    break;
		}
	
	
    case "4":{
	
	System.out.print("\nDIGITE O ID DO USUÁRIO QUE QUER EXCLUIR : ");
		    String email = sc.nextLine();
                    ArrayList<Usuario> usuarios = buscarUsuarios();

		    for (int i = 0; i < usuarios.size(); i++) {//percorre o array
			Usuario u = usuarios.get(i);// O 'u' recebe os valores de dentro do array

			if (u.getEmail().equals(email)) {

			    System.out.println("\n-------------------------------------------------> O USUÁRIO: " + u.getEmail() + " Nome: " + u.getNome() + "\n                                                   FOI ENCONTRADO.\n                                                   DESEJA REALMENTE EXLUIR ESSE CADASTRO? ");

			    System.out.println("\n                                                   [1] SIM");
			    System.out.println("                                                   [2] NÃO - VOLTAR PARA O MENU");
			    String escolhe = sc.nextLine();

			    switch (escolhe) {
				case "1": {

				    deletarUsuario(u);
				    System.out.println("\n-------------------------------------------------> O USUÁRIO FOI REMOVIDO COM SUCESSO!");
				    break;
				}
				case "2": {
				    System.out.println("\n-------------------------------------------------> O USUÁRIO NÃO FOI REMOVIDO! REDIRECIONADO PARA O MENU."); //ESSA MENSAGEM APARECE A QTD DE USUARIOS Q N FORAM REMOVIDOS
                                  break; // precisa desse break aqui
				}
			    }
			}else if (i == usuarios.size() - 1){
		                    System.out.println("\n-------------------------------------------------> EMAIL NÃO ENCONTRADO!");
		    }
		}break;
	    }
    
     case "5":{
	
	
        running = false;
	System.out.print("\nPrograma Finalizado!");
	break;
     }
    
    default: {
		    System.out.println("\n-------------------------------------------------> OPÇÃO INVÁLIDA! ..DIGITE UMA OPÇÃO VÁLIDA!");
	
        }
    if (true) {

                    System.out.println("Pressione enter para continuar");
                    sc.nextLine();
                }
    
     }
  }
}
   
	
   public static void inserirUsuario(Usuario user) {
        
        try {
            
            Driver driver = new Driver();
            DriverManager.registerDriver(driver);
            
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
            
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO bd_6javiu.usuarios (nome, email, senha) VALUES (?,?,?)");
            
	    stmt.setString(1, user.getNome ());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getSenha());
            
            int linhasAfetadas = stmt.executeUpdate();
            
            if (linhasAfetadas > 0) {
               // System.out.println("Usuário cadastrado com sucesso");
            } else {
              //  System.out.println("Não foi possível cadastrar o usuário. Tente novamente");
            }
            
            
        } catch(SQLException e ) {
            e.printStackTrace();
        }
        
    }

    public static ArrayList<Usuario> buscarUsuarios() {
        
        ArrayList<Usuario> usuarios = new ArrayList();
        try {

            Driver driver = new Driver();

            DriverManager.registerDriver(driver);

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");

            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM bd_6javiu.usuarios");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                int id = rs.getInt("id");
		String nome = rs.getString("nome");
                String email = rs.getString("email");
                String senha = rs.getString("senha");

                Usuario user = new Usuario();
                user.setId(id);
		user.setNome(nome);
                user.setEmail(email);
                user.setSenha(senha);
                
                usuarios.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return usuarios;
    }
    
    public static void atualizarUsuario(Usuario u) {
	
        try {
            
            Driver driver = new Driver();
            DriverManager.registerDriver(driver);
            
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
            
            PreparedStatement stmt = conn.prepareStatement("UPDATE bd_6javiu.usuarios SET Nome = ?, SET email = ?, senha = ? WHERE id = ?");
            stmt.setString(1, u.getNome());
            stmt.setString(2, u.getEmail());
            stmt.setString(3, u.getSenha());
	    stmt.setInt(4, u.getId());
	    
	    int linhasAfetadas = stmt.executeUpdate();
            
            if (linhasAfetadas > 0) {
                System.out.println("Usuário atualizado com sucesso");
            } else {
                System.out.println("Não foi possível atualizar os dados. Tente novamente");
            }
            
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void deletarUsuario(Usuario u) {
        
        try {
            Driver driver = new Driver();
            DriverManager.registerDriver(driver);
            
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
            
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM bd_6javiu.usuarios WHERE id = ?");
            
            stmt.setInt(1, u.getId());
            
            int linhasAfetadas = stmt.executeUpdate();
            
            if (linhasAfetadas > 0) {
                System.out.println("");
            } else {
                System.out.println("Não foi possível deletar o usuário. Tente novamente");
            }
            
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

}


