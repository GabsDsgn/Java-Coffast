package com.coffee.DAO;

import com.coffee.DTO.ProductDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ProductDAO {

    private Connection conn;
    private PreparedStatement pstm;
    private ResultSet rs;
    private ArrayList<ProductDTO> productList = new ArrayList<>();

    public void cadastrarProduto(ProductDTO product) {
        conn = new ConectionDAO().conectionDB();

        try {
            // Verificar se o produto já existe pelo nome (ou outra chave única)
            if (verificarProdutoExistente(product.getName_product())) {
                JOptionPane.showMessageDialog(null, "Produto já cadastrado. Escolha outro nome.");
                return; // Encerra o método se o produto já estiver cadastrado
            }

            // Se o produto não existir, procede com a inserção
            String sql = "INSERT INTO product(name_product, description_product) VALUES (?, ?)";
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, product.getName_product());
            pstm.setString(2, product.getDescription_product());

            pstm.execute();
            pstm.close();
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar novo produto: " + error);
        }
    }

    public ArrayList<ProductDTO> listarProdutos() {
        String sql = "SELECT * FROM product";
        conn = new ConectionDAO().conectionDB();

        try {
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();

            while (rs.next()) {
                ProductDTO productDTO = new ProductDTO();
                productDTO.setId_product(rs.getInt("id_product"));
                productDTO.setName_product(rs.getString("name_product"));
                productDTO.setDescription_product(rs.getString("description_product"));
                productList.add(productDTO);
            }

        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + error);
        }

        return productList;
    }

    private boolean verificarProdutoExistente(String productName) {
        try {
            String sql = "SELECT * FROM product WHERE name_product = ?";
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, productName);
            rs = pstm.executeQuery();

            // Se houver resultados, significa que o produto já está cadastrado
            return rs.next();
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar produto existente: " + error);
            return false;
        }
    }
    
    public ArrayList<ProductDTO> SearchProduct() {
        String sql = "select * from product";
        conn = new ConectionDAO().conectionDB();
        
        try {
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            
            while(rs.next()) {
                ProductDTO objProductDTO = new ProductDTO();
                objProductDTO.setId_product(rs.getInt("id_product"));
                objProductDTO.setName_product(rs.getString("name_product"));
                objProductDTO.setDescription_product(rs.getString("description_product"));
                
                productList.add(objProductDTO);
            } 
            
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, "ProductDAO Search: " + error);
        }
        return productList;
    }
    
    public void updateProduct(ProductDTO objProductDTO) {
        
        String sql = "update product set name_product = ?, description_product = ? where id_product = ?";
        conn = new ConectionDAO().conectionDB();
        
        try {
            
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, objProductDTO.getName_product());
            pstm.setString(2, objProductDTO.getDescription_product());
            pstm.setInt(3, objProductDTO.getId_product());
            
            pstm.execute();
            pstm.close();
            
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, "ProductDAO Update: " + error);
        }
        
    }
    
        public void deleteProduct(int ID) {
        String sql = "delete from product where id_product = ?";
        conn = new ConectionDAO().conectionDB();
   
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, ID);
            pstm.execute();
            pstm.close();

        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, "Error deleteProduct: " + error);

        }

    }
    
               public ResultSet listarProduct() {
        conn = new ConectionDAO().conectionDB();
        String sql = "Select name_product From product order by name_product";
        
        try {
            pstm = conn.prepareStatement(sql);
            return pstm.executeQuery();
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, "Error listarProduct: " + error);
            return null;
        }
    }

    public ProductDTO fetchOneProduct(String name_product) {
        String sql = "select * from product where name_product = ?";
        conn = new ConectionDAO().conectionDB();

        try {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, String.valueOf(name_product));
            rs = pstm.executeQuery();
            // pegando o nome do usuÃ¡rio
            ProductDTO productDTO = new ProductDTO();
            if (rs.next()) {
                productDTO.setName_product(rs.getString("name_product"));
                productDTO.setDescription_product(rs.getString("description_product"));
                productDTO.setId_product(rs.getInt("id_product"));

            } else {
                System.out.print("Erro no next do fetchOneProduct");
            }
            return productDTO;

        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, "Error fetchOneProduct: " + error);
            return null;
        }
    }
        
}
