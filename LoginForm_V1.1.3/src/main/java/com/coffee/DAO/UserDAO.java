/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coffee.DAO;

import com.coffee.DTO.UserDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author joaovictor
 */
public class UserDAO {

    // Atributos ------
    Connection conn;
    PreparedStatement pstm;
    // ResultSet representa o retorno do sql
    ResultSet rs;
    ArrayList<UserDTO> list = new ArrayList<>();

    // autenticacao de usuário
    public ResultSet userAutentication(UserDTO user) {
        conn = new ConectionDAO().conectionDB();
        try {
            // preparando a connection e criando a query de busca
            String sql = "select * from user where email_user = ? and password_user = ?";
            pstm = conn.prepareStatement(sql);
            // pegando o valor que desejo colocar na query - no lugar dos ?
            pstm.setString(1, user.getUser_email());
            pstm.setString(2, user.getPassword_usuario());
            // executando minha query
            rs = pstm.executeQuery();
            return rs;

        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error);
            // caso tenha erro ele dispara a mensagem e retorna null
            return null;
        }
    }

    // cadastrando um novo usuario
    public void cadastrarUsuario(UserDTO user) {
        conn = new ConectionDAO().conectionDB();

            try {
        // Verificar se o e-mail já está em uso
        if (verificarEmailExistente(user.getUser_email())) {
            JOptionPane.showMessageDialog(null, "E-mail já em uso. Escolha outro.");
            return; // Encerra o método se o e-mail já estiver em uso
        }

            // Se o e-mail não existir, procede com a inserção
            String sql = "insert into user(name_user,email_user,password_user) values (?,?,?)";
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, user.getUser_name());
            pstm.setString(2, user.getUser_email());
            pstm.setString(3, user.getPassword_usuario());

            pstm.execute();
            pstm.close();
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir novo usuário: " + error);
        }
    }

    
    private boolean verificarEmailExistente(String email) {
    try {
        String sql = "SELECT * FROM user WHERE email_user = ?";
        pstm = conn.prepareStatement(sql);
        pstm.setString(1, email);
        rs = pstm.executeQuery();

        // Se houver resultados, significa que o e-mail já está em uso
        return rs.next();
    } catch (SQLException error) {
        JOptionPane.showMessageDialog(null, "Erro ao verificar e-mail existente: " + error);
        return false; // Trate o erro de acordo com a sua lógica de tratamento de erros
    }
}

    
    // pesquisar por funcionário
    public ArrayList<UserDTO> fetchUser() {
        String sql = "select * from user";
        conn = new ConectionDAO().conectionDB();
        try {
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            // iterando todos os dados dentro do banco, para exibir todos os usuários cadastrados
            while (rs.next()) {
                UserDTO userDTO = new UserDTO();
                userDTO.setId_usuario(rs.getInt("id_user"));
                userDTO.setUser_name(rs.getString("name_user"));
                userDTO.setUser_email(rs.getString("email_user"));
                userDTO.setPassword_usuario(rs.getString("password_user"));
                // montando minha lista
                list.add(userDTO);

            }

        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, "Error fetchUser: " + error);
        }

        return list;
    }

    public UserDTO fetchOneUser(int id_user) {
        String sql = "select * from user where id_user = ?";
        conn = new ConectionDAO().conectionDB();
        System.out.println(String.valueOf(id_user));
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, String.valueOf(id_user));
            rs = pstm.executeQuery();
            // pegando o nome do usuÃ¡rio
            UserDTO userDTO = new UserDTO();
            if (rs.next()) {
                userDTO.setId_usuario(id_user);
                userDTO.setUser_name(rs.getString("name_user"));
            } else {
                System.out.print("Erro no next do fetchOneUser");
            }
            return userDTO;

        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, "Error fetchOneUser: " + error);
            return null;
        }
    }

    // atualizar os dados de um usuario
    public void updateUser(UserDTO user) {
        String sql = "update user set name_user = ? where id_user = ?";
        conn = new ConectionDAO().conectionDB();

        try {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, user.getUser_name());
            pstm.setString(2, String.valueOf(user.getId_usuario()));
            pstm.execute();
            pstm.close();

        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, "Error updateUser: " + error);

        }
    }
    
    public ResultSet listProduct() {
        conn = new ConectionDAO.conectionDB();
        String sql = "SELECT * FROM product ORDER BY description_product;";
        
        try {
            
            pstm = conn.prepareStatement(sql);
            return pstm.executeQuery();
            
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "ListProduct UserDAO" + erro.getMessage());
            return null;
        }
    }

    public void deleteUser(UserDTO user) {
        String sql = "delete from user where id_user = ?";
        conn = new ConectionDAO().conectionDB();
        // deletando primeiro o user presente como foreing key da tabela pedidos
        PedidoDAO pedidoDAO = new PedidoDAO();
        pedidoDAO.deleteUserKey(user);
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, user.getId_usuario());
            pstm.execute();
            pstm.close();
            System.out.print(user.getId_usuario());
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, "Error deleteUser: " + error);

        }

    }

    public Connection getConnection() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
