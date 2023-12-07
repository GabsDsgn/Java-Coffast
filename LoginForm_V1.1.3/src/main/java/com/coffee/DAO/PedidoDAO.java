/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coffee.DAO;

import com.coffee.DTO.PedidoDTO;
import com.coffee.DTO.ProductDTO;
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
public class PedidoDAO {
    // Atributos ------

    Connection conn;
    PreparedStatement pstm;
    // ResultSet representa o retorno do sql
    ResultSet rs;
    ArrayList<PedidoDTO> list = new ArrayList<>();

    // cadastrando um novo pedido
    public void fazerPedido(UserDTO user, ProductDTO product) {
        conn = new ConectionDAO().conectionDB();
        String sql = "insert into pedidos_clientes_produtos(id_user, id_product) values (?,?)";
        try {

            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, user.getId_usuario());
            pstm.setInt(2, product.getId_product());
            pstm.execute();
            pstm.close();
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir novo user: " + error);
        }
    }

    // pesquisar por todos os pedidos
    public ArrayList<PedidoDTO> fetchPedidos() {
        String sql = """
                     SELECT pedidos_clientes_produtos.id_pedido, user.name_user, product.name_product
                     FROM user
                     JOIN pedidos_clientes_produtos ON user.id_user = pedidos_clientes_produtos.id_user
                     JOIN product ON pedidos_clientes_produtos.id_product = product.id_product
                     ORDER BY id_pedido;""";
        conn = new ConectionDAO().conectionDB();
        try {
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();

            // Iterando todos os dados dentro do banco para exibir todos os usuÃ¡rios cadastrados
            while (rs.next()) {
                PedidoDTO pedidoDTO = new PedidoDTO();
                pedidoDTO.setId_pedido(rs.getInt("id_pedido"));
                pedidoDTO.setName_user(rs.getString("name_user"));
                pedidoDTO.setName_product(rs.getString("name_product"));

                // Montando minha lista
                list.add(pedidoDTO);
            }

        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, "Error fetchUser: " + error);
        }

        return list;
    }
    
    public void deleteUserKey(UserDTO user) {
         String sql = "delete from pedidos_clientes_produtos where id_user = ?";
        conn = new ConectionDAO().conectionDB();
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, user.getId_usuario());
            pstm.execute();
            pstm.close();
            System.out.print(user.getId_usuario());
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, "Error deleteUser dos produtos: " + error);

        }
    }
    
      public void deleteProductKey(ProductDTO product) {
         String sql = "delete from pedidos_clientes_produtos where id_product = ?";
        conn = new ConectionDAO().conectionDB();
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, product.getId_product());
            pstm.execute();
            pstm.close();
            System.out.print(product.getId_product());
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, "Error deleteProduct dos produtos: " + error);

        }
    }
}