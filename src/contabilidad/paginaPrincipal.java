/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contabilidad;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ramal
 */
public class paginaPrincipal extends javax.swing.JFrame {
    conexionDBs con = new conexionDBs();
    Connection cn = con.conexion();
    

    /**
     * Creates new form paginaPrincipal
     */
    public paginaPrincipal() {
        initComponents();
        mostrarInventario();
        mostrarVentas();
        mostrarPesos();
        mostrarMerca();
        mostrarCaja();
        mostrarDeuda();
        total();
        sumatoriaInventario();
        cargarCliente();
        cargarMerca();
        cargarPeso();
        
        
        
        
        
    }
    
    //Muestra el inventario en la ventana de inventarios
    void mostrarInventario(){
        DefaultTableModel client = new DefaultTableModel();
        client.addColumn("Id");
        client.addColumn("Nombre");
        client.addColumn("Cantidad");
        client.addColumn("Precio Estimado");
        

        

        String sqlv = "SELECT * FROM inventario";
        
        String datos[] = new String[4];

         
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sqlv);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
               
                client.addRow(datos);
            }
            tablaInventario.setModel(client);
        } catch (SQLException ex) {
            Logger.getLogger(paginaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }   
    //muestra ventas en Cuentas/listaVentas
    void mostrarVentas(){
        DefaultTableModel client = new DefaultTableModel();
        client.addColumn("Id");
        client.addColumn("cantidad");
        client.addColumn("precio");
        client.addColumn("clien");
        

        

        String sqlv = "SELECT * FROM ventas";
        
        String datos[] = new String[4];

         
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sqlv);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
               
                client.addRow(datos);
            }
            tablaVentas.setModel(client);
        } catch (SQLException ex) {
            Logger.getLogger(paginaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    //ventanas de administracion
    void mostrarPesos(){
        DefaultTableModel client = new DefaultTableModel();
        client.addColumn("Id");
        client.addColumn("Cantidad");
        client.addColumn("Precio");

        

        String sqlv = "SELECT * FROM pesos";
        
        String datos[] = new String[3];

         
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sqlv);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
               
                client.addRow(datos);
            }
            tablaPesos.setModel(client);
        } catch (SQLException ex) {
            Logger.getLogger(paginaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    void mostrarMerca(){
        DefaultTableModel client = new DefaultTableModel();
        client.addColumn("Id");
        

        

        String sqlv = "SELECT * FROM mercaderias";
        
        String datos[] = new String[1];

         
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sqlv);
            while (rs.next()) {
                datos[0] = rs.getString(1);
               
                client.addRow(datos);
            }
            tablaMerca.setModel(client);
        } catch (SQLException ex) {
            Logger.getLogger(paginaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    //muestra lista de ingresos al cash
    void mostrarCaja(){
        DefaultTableModel client = new DefaultTableModel();
        client.addColumn("Cash");
        
        String sqlv = "SELECT Precio FROM ventas";
        int sum=0;
        
        String datos[] = new String[1];

         
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sqlv);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                sum=sum+Integer.parseInt(datos[0]);
                
                client.addRow(datos);
            }
            tablaCaja.setModel(client);
           
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(paginaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        txtcashTotal.setText(Integer.toString(sum)); 
        txtCas.setText(Integer.toString(sum)); 
    }
    //muestra lista de ingreso de deudores
    void mostrarDeuda(){
        DefaultTableModel client = new DefaultTableModel();
        client.addColumn("Cliente");
        client.addColumn("Deuda");
        client.addColumn("Restante");
        client.addColumn("Pagado");
        
        String sqlv = "SELECT Nombre,Deuda,Restante,Pagado FROM clientes";
        int sum=0,supa=0;
        String datos[] = new String[4];

         
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sqlv);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
               
                sum=sum+Integer.parseInt(datos[1]);
                supa=supa+Integer.parseInt(datos[3]);
                
                client.addRow(datos);
            }
            tablaDeudos.setModel(client);
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(paginaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        txtPagos.setText(Integer.toString(supa));
        txtDeudasTotal.setText(Integer.toString(sum));
        txtDeu.setText(Integer.toString(sum));
    }
    //suma el capital total
    void total(){
        int ca,de,tot,ext;
        ext=Integer.parseInt(txtPagos.getText());
        ca=Integer.parseInt(txtCas.getText());
        de=Integer.parseInt(txtDeu.getText());
        tot =ca+de+ext;
        txtTotal.setText(Integer.toString(tot));
        
    }
    //calcula el inventario
    void sumatoriaInventario(){
        int topsum = tablaInventario.getRowCount();
        int topres = tablaVentas.getRowCount();
        
        
        
       
        int ini=0;
        int rest[] =new int[topres];
        int suma[] = new int[topsum];
        int sum =0,res=0, total=0;
        String sqlv = "SELECT * FROM inventario";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sqlv);
            while (rs.next()){
                suma[ini]=Integer.parseInt(rs.getString(3));
                sum=sum+suma[ini];
                ini++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(paginaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ini=0;
        String sqlvs = "SELECT * FROM ventas";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sqlvs);
            while (rs.next()){
                rest[ini]=Integer.parseInt(rs.getString(2));
                res=res+rest[ini];
                ini++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(paginaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        total=sum-res;
        txtCantTotal.setText(Integer.toString(total));
        
    }
    
    
    //cargas automaticas para combobox
    void cargarCliente() {
        DefaultTableModel servi = new DefaultTableModel();

        servi.addColumn("Nombre");
        String sqls = "SELECT Nombre FROM clientes";
        String dator[] = new String[1];
        try {
            Statement sr = cn.createStatement();
            ResultSet rr = sr.executeQuery(sqls);
            while (rr.next()) {
                dator[0] = rr.getString(1);
                servi.addRow(dator);

                selecCliente.addItem(dator[0]);
                selecCliente1.addItem(dator[0]);
                
            }

        } catch (SQLException ex) {
            Logger.getLogger(paginaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    void cargarMerca() {
        DefaultTableModel servi = new DefaultTableModel();

        servi.addColumn("Nombre");
        String sqls = "SELECT Nombre FROM mercaderias";
        String dator[] = new String[1];
        try {
            Statement sr = cn.createStatement();
            ResultSet rr = sr.executeQuery(sqls);
            while (rr.next()) {
                dator[0] = rr.getString(1);
                servi.addRow(dator);

                selecTipoVenta.addItem(dator[0]);
                selecTipoVenta1.addItem(dator[0]);
                selecTipo.addItem(dator[0]);
                
            }

        } catch (SQLException ex) {
            Logger.getLogger(paginaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    void cargarPeso() {
        DefaultTableModel servi = new DefaultTableModel();

        servi.addColumn("Nombre");
        String sqls = "SELECT Nombre FROM pesos";
        String dator[] = new String[1];
        try {
            Statement sr = cn.createStatement();
            ResultSet rr = sr.executeQuery(sqls);
            while (rr.next()) {
                dator[0] = rr.getString(1);
                selecCantidad.addItem(dator[0]); 
                selecCantidad1.addItem(dator[0]); 
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(paginaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        btnVenta = new javax.swing.JButton();
        selecTipoVenta = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        txtCliente = new javax.swing.JTextField();
        selecCantidad = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaVentas = new javax.swing.JTable();
        jPanel21 = new javax.swing.JPanel();
        txtPago = new javax.swing.JTextField();
        txtDebe1 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        btnPago = new javax.swing.JButton();
        selecCliente = new javax.swing.JComboBox<>();
        btnCarga = new javax.swing.JButton();
        txtRest = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        txtDebe = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtAdelanto = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        btnEnvioCredito = new javax.swing.JButton();
        selecCantidad1 = new javax.swing.JComboBox<>();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        txtPrecio1 = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        selecTipoVenta1 = new javax.swing.JComboBox<>();
        selecCliente1 = new javax.swing.JComboBox<>();
        btnCarga2 = new javax.swing.JButton();
        sda = new javax.swing.JTabbedPane();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tablaDeudos = new javax.swing.JTable();
        jLabel23 = new javax.swing.JLabel();
        txtDeudasTotal = new javax.swing.JTextField();
        txtPagos = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        txtCas = new javax.swing.JTextField();
        txtDeu = new javax.swing.JTextField();
        txtTotal = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablaCaja = new javax.swing.JTable();
        jLabel22 = new javax.swing.JLabel();
        txtcashTotal = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaInventario = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        txtCantTotal = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtCantiIngreso = new javax.swing.JTextField();
        txtPrecioEstim = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtFechaIngre = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtTotalPago = new javax.swing.JTextField();
        txtPagado = new javax.swing.JTextField();
        txtDeuda = new javax.swing.JTextField();
        btnIngreso = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        selecTipo = new javax.swing.JComboBox<>();
        jPanel16 = new javax.swing.JPanel();
        jTabbedPane6 = new javax.swing.JTabbedPane();
        jPanel17 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaMerca = new javax.swing.JTable();
        btnAgregarMerca = new javax.swing.JButton();
        btnEliminarMerca = new javax.swing.JButton();
        btnActualizarmerca = new javax.swing.JButton();
        btnActualizarPeso = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaPesos = new javax.swing.JTable();
        btnNuevoPeso = new javax.swing.JButton();
        btnEliminarPeso = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel19 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jTabbedPane1.setBackground(new java.awt.Color(0, 0, 102));

        jPanel2.setBackground(new java.awt.Color(0, 0, 102));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 415, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 124, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(356, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Inicio", jPanel2);

        jPanel3.setBackground(new java.awt.Color(0, 0, 102));

        jLabel1.setText("Cantidad:");

        jLabel2.setText("Precio:");

        btnVenta.setText("Venta");
        btnVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVentaActionPerformed(evt);
            }
        });

        jLabel10.setText("Cliente:");

        jLabel13.setText("Tipo:");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(256, 256, 256)
                        .addComponent(btnVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtPrecio)
                                .addComponent(selecCantidad, 0, 112, Short.MAX_VALUE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel13)
                                .addGap(18, 18, 18)
                                .addComponent(selecTipoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(selecCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selecTipoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnVenta)
                .addContainerGap(44, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Ventas", jPanel6);

        tablaVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane5.setViewportView(tablaVentas);

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Lista de Ventas", jPanel20);

        jLabel19.setText("Deuda:");

        jLabel20.setText("Pago:");

        jLabel21.setText("Cliente:");

        btnPago.setText("Pagar");
        btnPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPagoActionPerformed(evt);
            }
        });

        btnCarga.setText("jButton1");
        btnCarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jLabel20)
                        .addGap(18, 18, 18)
                        .addComponent(txtPago)
                        .addGap(51, 51, 51)
                        .addComponent(btnPago, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel21)
                        .addGap(18, 18, 18)
                        .addComponent(selecCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCarga, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtRest, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                            .addComponent(txtDebe1))))
                .addContainerGap())
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jLabel19)
                    .addComponent(txtDebe1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selecCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCarga))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtRest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPago)
                    .addComponent(txtPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addGap(35, 35, 35))
        );

        jTabbedPane3.addTab("Pagos", jPanel21);

        jLabel12.setText("Deuda:");

        jLabel11.setText("Adelento:");

        btnEnvioCredito.setText("Cargar");
        btnEnvioCredito.setActionCommand("Cargar");
        btnEnvioCredito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnvioCreditoActionPerformed(evt);
            }
        });

        jLabel27.setText("Cantidad:");

        jLabel28.setText("Precio:");

        jLabel29.setText("Cliente:");

        jLabel30.setText("Tipo:");

        btnCarga2.setText("jButton1");
        btnCarga2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCarga2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27)
                            .addComponent(jLabel28))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtPrecio1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addComponent(selecCantidad1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnCarga2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addGap(18, 18, 18)
                        .addComponent(selecCliente1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel14Layout.createSequentialGroup()
                            .addComponent(jLabel30)
                            .addGap(18, 18, 18)
                            .addComponent(selecTipoVenta1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel14Layout.createSequentialGroup()
                            .addComponent(jLabel11)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtAdelanto, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel12)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtDebe, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnEnvioCredito, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(selecCliente1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(txtDebe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAdelanto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(selecCantidad1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCarga2)
                    .addComponent(selecTipoVenta1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(txtPrecio1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEnvioCredito))
                .addContainerGap(69, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("credito", jPanel14);

        tablaDeudos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane7.setViewportView(tablaDeudos);

        jLabel23.setText("Total:");

        txtPagos.setToolTipText("");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel23)
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtDeudasTotal)
                    .addComponent(txtPagos, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE))
                .addContainerGap(55, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(txtDeudasTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addComponent(txtPagos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        sda.addTab("Deudas", jPanel9);

        jLabel24.setText("Cash");

        jLabel25.setText("Deudas");

        jLabel26.setText("Total");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(txtTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel26))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtDeu, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                            .addComponent(txtCas))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap(259, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addGap(39, 39, 39)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDeu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26))
                .addGap(45, 45, 45))
        );

        sda.addTab("Total", jPanel7);

        tablaCaja.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane6.setViewportView(tablaCaja);

        jLabel22.setText("Total:");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jLabel22)
                .addGap(18, 18, 18)
                .addComponent(txtcashTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(119, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(txtcashTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        sda.addTab("Caja", jPanel8);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(sda, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addComponent(sda, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Cuentas", jPanel3);

        jPanel4.setBackground(new java.awt.Color(0, 0, 102));

        jPanel12.setBackground(new java.awt.Color(102, 102, 102));

        tablaInventario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tablaInventario);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("Lista de Ingresos");

        jLabel18.setText("Stock Total:");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel18)
                        .addGap(40, 40, 40)
                        .addComponent(txtCantTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCantTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Inventario", jPanel5);

        jLabel3.setText("Cantidad:");

        jLabel4.setText("Precio estimado:");

        jLabel5.setText("Fecha:");

        jLabel6.setText("Total a pagar:");

        jLabel7.setText("Total pagado:");

        jLabel8.setText("Deuda:");

        btnIngreso.setText("Ingresar");
        btnIngreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresoActionPerformed(evt);
            }
        });

        jPanel13.setBackground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 168, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 175, Short.MAX_VALUE)
        );

        jLabel17.setText("Tipo:");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtCantiIngreso)
                    .addComponent(txtPrecioEstim)
                    .addComponent(txtFechaIngre)
                    .addComponent(txtTotalPago)
                    .addComponent(txtPagado)
                    .addComponent(txtDeuda, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                    .addComponent(selecTipo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnIngreso, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(selecTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtCantiIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtPrecioEstim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtFechaIngre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtTotalPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtPagado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtDeuda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnIngreso)))
                .addContainerGap(122, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Nuevo ingreso", jPanel11);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(110, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Inventario", jPanel4);

        jPanel16.setBackground(new java.awt.Color(0, 0, 102));

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 410, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 441, Short.MAX_VALUE)
        );

        jTabbedPane6.addTab("General", jPanel17);

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("Mercaderias");

        tablaMerca.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tablaMerca);

        btnAgregarMerca.setText("Nueva");
        btnAgregarMerca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarMercaActionPerformed(evt);
            }
        });

        btnEliminarMerca.setText("Eliminar");
        btnEliminarMerca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarMercaActionPerformed(evt);
            }
        });

        btnActualizarmerca.setText("Actualizar");
        btnActualizarmerca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarmercaActionPerformed(evt);
            }
        });

        btnActualizarPeso.setText("Actualizar");
        btnActualizarPeso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarPesoActionPerformed(evt);
            }
        });

        tablaPesos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane4.setViewportView(tablaPesos);

        btnNuevoPeso.setText("Nuevo");
        btnNuevoPeso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoPesoActionPerformed(evt);
            }
        });

        btnEliminarPeso.setText("Eliminar");
        btnEliminarPeso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarPesoActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("Pesos y Precios");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel16))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                        .addGap(0, 13, Short.MAX_VALUE)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnEliminarMerca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnActualizarmerca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnAgregarMerca, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnNuevoPeso, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(btnEliminarPeso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnActualizarPeso)))))))
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(btnAgregarMerca)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEliminarMerca)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnActualizarmerca))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(btnNuevoPeso)
                        .addGap(11, 11, 11)
                        .addComponent(btnEliminarPeso)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnActualizarPeso))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 158, Short.MAX_VALUE))
        );

        jTabbedPane6.addTab("Mercaderia, Precios y Pesos", jPanel18);

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 410, Short.MAX_VALUE)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 441, Short.MAX_VALUE)
        );

        jTabbedPane6.addTab("Usuarios", jPanel19);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane6)
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane6)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Configuración", jPanel16);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    //controlador de ingreso de mercaderia al inventario
    private void btnIngresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresoActionPerformed
        int cant, canti;
        String ca;
        cant = Integer.parseInt(txtCantiIngreso.getText());
        
        canti=cant*1000;
        ca = Integer.toString(canti);
        try {
                PreparedStatement ppn = cn.prepareStatement("INSERT INTO inventario(Tipo,Cantidad,PrecioEstimado,Fecha,Total,Adelanto,Deuda)VALUES(?,?,?,?,?,?,?)");
                ppn.setString(1, selecTipo.getSelectedItem().toString());
                ppn.setString(2, ca);
                ppn.setString(3, txtPrecioEstim.getText());
                ppn.setString(4, txtFechaIngre.getText());
                ppn.setString(5, txtTotalPago.getText());
                ppn.setString(6, txtPagado.getText());
                ppn.setString(7, txtDeuda.getText());
                ppn.executeUpdate();
                JOptionPane.showMessageDialog(null, "Datos Guardados");
                mostrarInventario();
                sumatoriaInventario();
          } catch (SQLException ex) {
              Logger.getLogger(paginaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
          }
        
    }//GEN-LAST:event_btnIngresoActionPerformed

    
    
    
    //controles de pagos
    private void btnCargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargaActionPerformed
        String valor = selecCliente.getSelectedItem().toString();
        DefaultTableModel servi = new DefaultTableModel();

        servi.addColumn("Restante");

        String  sqlv = "SELECT Restante FROM clientes WHERE Nombre='" + valor + "'";
        
        String dator[] = new String[1];
        try {
            Statement sr = cn.createStatement();
            ResultSet rr = sr.executeQuery(sqlv);
            while (rr.next()) {
                txtDebe1.setText(rr.getString(1));
               

                servi.addRow(dator);

            }

        } catch (SQLException ex) {
            Logger.getLogger(paginaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        DefaultTableModel servis = new DefaultTableModel();

        servis.addColumn("Deuda");
        
        String  sqlvs = "SELECT Deuda FROM clientes WHERE Nombre='" + valor + "'";
        
        String dators[] = new String[1];
        try {
            Statement sr = cn.createStatement();
            ResultSet rr = sr.executeQuery(sqlvs);
            while (rr.next()) {
                txtRest.setText(rr.getString(1));
               

                servis.addRow(dators);

            }

        } catch (SQLException ex) {
            Logger.getLogger(paginaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnCargaActionPerformed
    private void btnPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPagoActionPerformed
        int deu, pago, total,rest,sobra;
        String tot, pag;
        deu = Integer.parseInt(txtDebe1.getText());
        pago = Integer.parseInt(txtPago.getText());
        rest = Integer.parseInt(txtRest.getText());
        total=deu-pago;
        tot=Integer.toString(total);
        sobra=rest-total;
        pag=Integer.toString(sobra);
            PreparedStatement ppf;
        try {
            ppf = cn.prepareStatement("UPDATE clientes SET Restante='" + tot + "',Pagado='"+pag+"' WHERE Nombre='" + selecCliente.getSelectedItem().toString() + "'");
            ppf.executeUpdate();
            JOptionPane.showMessageDialog(null, "Datos actulizados");
            mostrarDeuda();
            mostrarCaja();
            total();
        } catch (SQLException ex) {
            Logger.getLogger(paginaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }//GEN-LAST:event_btnPagoActionPerformed

    
    
    //controladores de administracion
    private void btnAgregarMercaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarMercaActionPerformed
        nuevMerc abrir = new nuevMerc();
        abrir.setVisible(true);
        mostrarMerca();
        cargarMerca();
    }//GEN-LAST:event_btnAgregarMercaActionPerformed
    private void btnEliminarMercaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarMercaActionPerformed
        int fila = tablaMerca.getSelectedRow();
        String valor = tablaMerca.getValueAt(fila, 0).toString();
        if (fila >= 0) {
            try {
                PreparedStatement pps = cn.prepareStatement("DELETE FROM mercaderias WHERE Nombre='" + valor + "'");
                pps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Datos eliminados");
                mostrarMerca();
               cargarMerca();
            } catch (SQLException ex) {
                Logger.getLogger(paginaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_btnEliminarMercaActionPerformed
    private void btnNuevoPesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoPesoActionPerformed
          nuevPeso abrir =new nuevPeso();
        abrir.setVisible(true);
        mostrarPesos();
         cargarPeso();
    }//GEN-LAST:event_btnNuevoPesoActionPerformed
    private void btnEliminarPesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarPesoActionPerformed
        int fila = tablaPesos.getSelectedRow();
        String valor = tablaPesos.getValueAt(fila, 0).toString();
        if (fila >= 0) {
            try {
                PreparedStatement pps = cn.prepareStatement("DELETE FROM pesos WHERE Nombre='" + valor + "'");
                pps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Datos eliminados");
                mostrarPesos();
               cargarPeso();
            } catch (SQLException ex) {
                Logger.getLogger(paginaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_btnEliminarPesoActionPerformed
    private void btnActualizarmercaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarmercaActionPerformed
        mostrarMerca();
        cargarMerca();
    }//GEN-LAST:event_btnActualizarmercaActionPerformed
    private void btnActualizarPesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarPesoActionPerformed
        mostrarPesos();
        cargarPeso();
    }//GEN-LAST:event_btnActualizarPesoActionPerformed

    
    
    //controladores de ventas
    
    private void btnVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVentaActionPerformed
        String slrow = selecCantidad.getSelectedItem().toString();
        int desc=0;
        String sqlv = "SELECT * FROM pesos WHERE Nombre='" + slrow + "'";
        
        try {
            Statement sr = cn.createStatement();
            ResultSet rr = sr.executeQuery(sqlv);
            while (rr.next()) {
                desc = Integer.parseInt(rr.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(paginaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            PreparedStatement ppn = cn.prepareStatement("INSERT INTO ventas(Cantidad,Precio,Cliente,Tipo)VALUES(?,?,?,?,?,?)");
            ppn.setString(1, Integer.toString(desc));
            ppn.setString(2, txtPrecio.getText());
            ppn.setString(3, txtCliente.getText());
            ppn.setString(4, selecTipoVenta.getSelectedItem().toString());
           
            ppn.executeUpdate();
            JOptionPane.showMessageDialog(null, "Datos Guardados");
            mostrarVentas();
            mostrarCaja();
            mostrarDeuda();
            total();
            sumatoriaInventario();
        } catch (SQLException ex) {
            Logger.getLogger(paginaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnVentaActionPerformed

    
     //controladores credito
    private void btnEnvioCreditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnvioCreditoActionPerformed
        //tenglo los valores del prestamo
        int precio,adela,restante;
        precio=Integer.parseInt(txtPrecio1.getText());
        adela=Integer.parseInt(txtAdelanto.getText());
        restante=precio-adela;
        
        //consigo los valores antiguos del deudor
        
        
        
        
        
        
        
        
        String valor = selecCliente1.getSelectedItem().toString();
        int dt[] = new int[3]; 
        String  sqlv = "SELECT Deuda,Restante,Pagado FROM clientes WHERE Nombre='" + valor + "'";
        try {
            Statement sr = cn.createStatement();
            ResultSet rr = sr.executeQuery(sqlv);
            while (rr.next()) {
                dt[0]=Integer.parseInt(rr.getString(1));
                dt[1]=Integer.parseInt(rr.getString(2));
                dt[2]=Integer.parseInt(rr.getString(3));
            }
        } catch (SQLException ex) {
            Logger.getLogger(paginaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        dt[0]=dt[0]+precio;
        dt[1]=dt[1]+restante;
        dt[2]=dt[2]+adela;
   
            PreparedStatement ppf;
        try {
            ppf = cn.prepareStatement("UPDATE clientes SET Deuda='" + dt[0] + "',Restante='" + dt[1] + "',Pagado='"+dt[2]+"' WHERE Nombre='" + selecCliente1.getSelectedItem().toString() + "'");
            ppf.executeUpdate();
            JOptionPane.showMessageDialog(null, "Datos actulizados");
            mostrarDeuda();
            mostrarCaja();
            total();
        } catch (SQLException ex) {
            Logger.getLogger(paginaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        
        
    }//GEN-LAST:event_btnEnvioCreditoActionPerformed
    private void btnCarga2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCarga2ActionPerformed
         String valor = selecCantidad1.getSelectedItem().toString();
        DefaultTableModel servi = new DefaultTableModel();

        servi.addColumn("Precio");

        String  sqlv = "SELECT Precio FROM pesos WHERE Nombre='" + valor + "'";
        
        String dator[] = new String[1];
        try {
            Statement sr = cn.createStatement();
            ResultSet rr = sr.executeQuery(sqlv);
            while (rr.next()) {
                txtPrecio1.setText(rr.getString(1));
               

                servi.addRow(dator);

            }

        } catch (SQLException ex) {
            Logger.getLogger(paginaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnCarga2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(paginaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(paginaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(paginaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(paginaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new paginaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizarPeso;
    private javax.swing.JButton btnActualizarmerca;
    private javax.swing.JButton btnAgregarMerca;
    private javax.swing.JButton btnCarga;
    private javax.swing.JButton btnCarga2;
    private javax.swing.JButton btnEliminarMerca;
    private javax.swing.JButton btnEliminarPeso;
    private javax.swing.JButton btnEnvioCredito;
    private javax.swing.JButton btnIngreso;
    private javax.swing.JButton btnNuevoPeso;
    private javax.swing.JButton btnPago;
    private javax.swing.JButton btnVenta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane6;
    private javax.swing.JTabbedPane sda;
    private javax.swing.JComboBox<String> selecCantidad;
    private javax.swing.JComboBox<String> selecCantidad1;
    private javax.swing.JComboBox<String> selecCliente;
    private javax.swing.JComboBox<String> selecCliente1;
    private javax.swing.JComboBox<String> selecTipo;
    private javax.swing.JComboBox<String> selecTipoVenta;
    private javax.swing.JComboBox<String> selecTipoVenta1;
    private javax.swing.JTable tablaCaja;
    private javax.swing.JTable tablaDeudos;
    private javax.swing.JTable tablaInventario;
    private javax.swing.JTable tablaMerca;
    private javax.swing.JTable tablaPesos;
    private javax.swing.JTable tablaVentas;
    private javax.swing.JTextField txtAdelanto;
    private javax.swing.JTextField txtCantTotal;
    private javax.swing.JTextField txtCantiIngreso;
    private javax.swing.JTextField txtCas;
    private javax.swing.JTextField txtCliente;
    private javax.swing.JTextField txtDebe;
    private javax.swing.JTextField txtDebe1;
    private javax.swing.JTextField txtDeu;
    private javax.swing.JTextField txtDeuda;
    private javax.swing.JTextField txtDeudasTotal;
    private javax.swing.JTextField txtFechaIngre;
    private javax.swing.JTextField txtPagado;
    private javax.swing.JTextField txtPago;
    private javax.swing.JTextField txtPagos;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtPrecio1;
    private javax.swing.JTextField txtPrecioEstim;
    private javax.swing.JTextField txtRest;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtTotalPago;
    private javax.swing.JTextField txtcashTotal;
    // End of variables declaration//GEN-END:variables
}
