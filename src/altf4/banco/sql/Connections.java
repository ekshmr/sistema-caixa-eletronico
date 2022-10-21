package altf4.banco.sql;

import altf4.banco.Acessos;
import altf4.banco.Transferencias;
import altf4.banco.UserInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Connections {

    public boolean cadastro(String getName, String getSobrenome, String getCPF, String getNasc, String getRG, String getLogin, String getSenha){
        
        Connection con = ConnectionFactory.getDb();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String vercpf = "", verrg = "", veruser = "";
        boolean sucess = false;
        
        try {
            ps = con.prepareStatement("call Cadastro(?,?,?,?,?,?,?);");
            
            ps.setString(1, getName);
            ps.setString(2, getSobrenome);
            ps.setDouble(3, Double.parseDouble(getCPF));
            ps.setString(4, getNasc);
            ps.setDouble(5, Double.parseDouble(getRG));
            ps.setString(6, getLogin);
            ps.setString(7, getSenha);
            
            rs = ps.executeQuery("SELECT cpf,rg,username FROM user");
            
            while(rs.next()){
                
                vercpf = rs.getString("cpf");
                verrg = rs.getString("rg");
                veruser = rs.getString("username");
            }
            if(vercpf.equals(getCPF)
                ||verrg.equals(getRG)
                    ||veruser.equals(getLogin)){
                
                JOptionPane.showMessageDialog(null, "Erro no cadastro, usuário já existente.\nRealize o login!", "ERRO", JOptionPane.ERROR_MESSAGE, null);
            }else{
            
                sucess = true;
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Cadastro realizado! Realize o Login.", "SUCESSO", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/altf4/banco/icons/Logo.png")));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro no cadastro: " + ex, "ERRO", JOptionPane.ERROR_MESSAGE, null);
        } finally {
            
            ConnectionFactory.closeConnection(con, ps, rs);
        }
        return sucess;
    }
    
        public void transferencia(String getSID, String getDID, double valor){
        
        Connection con = ConnectionFactory.getDb();
        PreparedStatement ps = null;
        
        try {
            ps = con.prepareStatement("call Transferencia(?,?,?);");
            
            ps.setString(1, getSID);
            ps.setString(2, getDID);
            ps.setDouble(3, valor);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Transferência realizada!", "SUCESSO", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/altf4/banco/icons/Logo.png")));
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro na transerência: " + ex, "ERRO", JOptionPane.ERROR_MESSAGE, null);
        } finally {
            
            ConnectionFactory.closeConnection(con, ps);
        }
    }
        
    public void sacar(String getSID, double valor){
        
        Connection con = ConnectionFactory.getDb();
        PreparedStatement ps = null;
        
        try {
            ps = con.prepareStatement("call Saque(?,?);");
            ps.setString(1, getSID);
            ps.setDouble(2, valor);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Saque programado!", "SUCESSO", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/altf4/banco/icons/Logo.png")));
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro no saque: " + ex, "ERRO", JOptionPane.ERROR_MESSAGE, null);
        } finally {
            ConnectionFactory.closeConnection(con, ps);
        }
    }
    
    public void depositar(String getSID, double valor){
        
        Connection con = ConnectionFactory.getDb();
        PreparedStatement ps = null;
        
        try {
            ps = con.prepareStatement("call Deposito(?,?);");
            ps.setString(1, getSID);
            ps.setDouble(2, valor);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Deposito rápido realizado!\nVocê transferiu R$ " + valor + " de seu outro banco.", "SUCESSO", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/altf4/banco/icons/Logo.png")));
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro no deposito: " + ex, "ERRO", JOptionPane.ERROR_MESSAGE, null);
        } finally {
            ConnectionFactory.closeConnection(con, ps);
        }
    }
    
    public void retirar(String getSID, double valor){
        
        Connection con = ConnectionFactory.getDb();
        PreparedStatement ps = null;
        
        try {
            ps = con.prepareStatement("call Retirada(?,?);");
            
            ps.setString(1, getSID);
            ps.setDouble(2, valor);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Saque cancelado, valor retornou ao saldo!", "SUCESSO", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/altf4/banco/icons/Logo.png")));
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro no saque: " + ex, "ERRO", JOptionPane.ERROR_MESSAGE, null);
        } finally {
            ConnectionFactory.closeConnection(con, ps);
        }
    }
    
    public void setSenha(String senha){
        
        Connection con = ConnectionFactory.getDb();
        PreparedStatement ps = null;
        
        try {
            ps = con.prepareStatement("UPDATE login join user on login.username=user.username SET login.senha = ? WHERE user.id = "+UserInfo.getUserID()+";");
            
            ps.setString(1, senha);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Senha alterada! Realize o login novamente.", "SUCESSO", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/altf4/banco/icons/Logo.png")));
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro na alteração: " + ex, "ERRO", JOptionPane.ERROR_MESSAGE, null);
        } finally {
            ConnectionFactory.closeConnection(con, ps);
        }
    }
    
    public boolean login(String login, String pass){
        
       Connection con = ConnectionFactory.getDb();
       PreparedStatement ps = null;
       ResultSet rs = null;
       String nome;
       String senha;
       int id;
       boolean passed = false;
       
        try {
            ps = con.prepareStatement("call Login('" + login +"', '"+ pass+"');");
            rs = ps.executeQuery();
            
            while(rs.next()){
                
                id = rs.getInt("user.id");
                nome = rs.getString("user.username");
                senha = rs.getString("login.senha");
                
                if(login.equals(nome) && pass.equals(senha)){
                    passed = true;
                    UserInfo.setUserID(String.valueOf(id));

                }else{
                    passed = false;
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERRO: " + ex, "ERRO", JOptionPane.ERROR_MESSAGE, null);
        }finally{
            ConnectionFactory.closeConnection(con, ps, rs);
        }
        if(passed == false){
            
            JOptionPane.showMessageDialog(null, "Usuário ou senha incorreto(s): ", "ERRO", JOptionPane.ERROR_MESSAGE, null);
        }else{
            JOptionPane.showMessageDialog(null, "Login realizado com sucesso!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/altf4/banco/icons/Logo.png")));
        }
        return passed;
    }
    
    public double getSaldo(){
        
        double saldo = 0;
        Connection con = ConnectionFactory.getDb();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = con.prepareStatement("SELECT saldo FROM user WHERE id = " + UserInfo.getUserID());
            rs = ps.executeQuery();
            
            while(rs.next()){
                saldo = rs.getDouble("saldo");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Connections.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            ConnectionFactory.closeConnection(con, ps, rs);
    }
        return saldo;
    }
    
    public String getSenha(){
        
        String senha = "";
        Connection con = ConnectionFactory.getDb();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = con.prepareStatement("SELECT login.senha FROM login join user on login.username = user.username WHERE user.id = " + UserInfo.getUserID());
            rs = ps.executeQuery();
            
            while(rs.next()){
                senha = rs.getString("senha");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Connections.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            ConnectionFactory.closeConnection(con, ps, rs);
    }
        return senha;
    }
    
    public ArrayList<Transferencias> getSendTransfers(){
        
        Connection con = ConnectionFactory.getDb();
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Transferencias> t = new ArrayList<>();
        double valor;
        int destino, id;
        String data, recebedor;
        
        try {
            ps = con.prepareStatement("select movimentacao.valor,movimentacao.destino_id,movimentacao.id,movimentacao.data,user.nome,user.sobrenome "
                    + "from user join movimentacao on user.id = movimentacao.destino_id where movimentacao.envio_id = "+UserInfo.getUserID()+";");
            rs = ps.executeQuery();
            
            while(rs.next()){
                
                Transferencias tr = new Transferencias();
                valor = rs.getDouble("movimentacao.valor");
                destino = rs.getInt("movimentacao.destino_id");
                id = rs.getInt("movimentacao.id");
                data = rs.getString("movimentacao.data");
                recebedor = rs.getString("user.nome");
                recebedor += " ";
                recebedor += rs.getString("user.sobrenome");
            
                tr.setTransfers(valor, destino, id, data, recebedor);
                t.add(tr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Connections.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            ConnectionFactory.closeConnection(con, ps, rs);
    }
        return t;
    }
    
    public ArrayList<Acessos> getAcessos(){
        
        Connection con = ConnectionFactory.getDb();
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Acessos> a = new ArrayList<>();
        int id;
        String data;
        
        try {
            ps = con.prepareStatement("select acesso.id,acesso.data from acesso join user "
                    + "on user.username = acesso.username where user.id = "+UserInfo.getUserID()+";");
            rs = ps.executeQuery();
            
            while(rs.next()){
                
                Acessos ac = new Acessos();
                id = rs.getInt("acesso.id");
                data = rs.getString("acesso.data");
            
                ac.setAcess(id, data);
                a.add(ac);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Connections.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            ConnectionFactory.closeConnection(con, ps, rs);
    }
        return a;
    }
    
    public ArrayList<Transferencias> getLastSendTransfer(){
        
        Connection con = ConnectionFactory.getDb();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Transferencias> t = new ArrayList<>();
        Transferencias tr = new Transferencias();
        double valor;
        String recebedor;
        
        try {
            ps = con.prepareStatement("select movimentacao.valor,user.nome,user.sobrenome from user "
                    + "join movimentacao on user.id = movimentacao.destino_id where movimentacao.envio_id = "+UserInfo.getUserID()+";");
            rs = ps.executeQuery();
            
            while(rs.next()){
                
                valor = rs.getDouble("movimentacao.valor");
                recebedor = rs.getString("user.nome");
                recebedor += " ";
                recebedor += rs.getString("user.sobrenome");
            
                tr.setLastTransfer(valor, recebedor);
                t.add(tr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Connections.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
        
            ConnectionFactory.closeConnection(con, ps, rs);
    }
        return t;
    }
    
    public ArrayList<Transferencias> getRecivedTransfers(){
        
        Connection con = ConnectionFactory.getDb();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Transferencias> t = new ArrayList<>();
        double valor;
        int destino, id;
        String data, recebedor;
        
        try {
            ps = con.prepareStatement("select movimentacao.valor,movimentacao.destino_id,movimentacao.id,movimentacao.data,user.nome,user.sobrenome "
                    + "from user join movimentacao on user.id = movimentacao.envio_id where movimentacao.destino_id = "+UserInfo.getUserID()+";");
            rs = ps.executeQuery();
            
            while(rs.next()){
                Transferencias tr = new Transferencias();
                valor = rs.getDouble("movimentacao.valor");
                destino = rs.getInt("movimentacao.destino_id");
                id = rs.getInt("movimentacao.id");
                data = rs.getString("movimentacao.data");
                recebedor = rs.getString("user.nome");
                recebedor += " ";
                recebedor += rs.getString("user.sobrenome");
            
                tr.setTransfers(valor, destino, id, data, recebedor);
                t.add(tr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Connections.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
        
            ConnectionFactory.closeConnection(con, ps, rs);
    }
        return t;
    }
    
    public double getLiberado(){
        
        double saldo = 0;
        Connection con = ConnectionFactory.getDb();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = con.prepareStatement("SELECT retirada FROM user WHERE id = " + UserInfo.getUserID());
            rs = ps.executeQuery();
            
            while(rs.next()){
                saldo = rs.getDouble("retirada");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Connections.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            ConnectionFactory.closeConnection(con, ps, rs);
    }
        return saldo;
    }
    
    public String getNome(){
        
        String nome = "";
        Connection con = ConnectionFactory.getDb();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = con.prepareStatement("SELECT nome, sobrenome FROM user WHERE id = " + UserInfo.getUserID());
            rs = ps.executeQuery();
            
            while(rs.next()){
                nome = rs.getString("nome");
                nome += (" ");
                nome += rs.getString("sobrenome");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Connections.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
        
            ConnectionFactory.closeConnection(con, ps, rs);
    }
        return nome;
    }
}