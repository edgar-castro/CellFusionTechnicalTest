package view;

import controller.ProductController;
import controller.WarehouseController;
import model.Product;
import model.Warehouse;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

public class WarehouseTab extends JPanel {

    private Warehouse currentWarehouse;
    private DefaultListModel<Warehouse> defaultListModel;
    private JList<Warehouse> list;
    private WarehouseController warehouseController;
    private JComboBox<Product> products;
    private JButton btn_new, btn_update, btn_delete, btn_clear;
    private JTextField input_name, input_min, input_max;
    private GridBagConstraints gbc;

    public WarehouseTab() {
        warehouseController = new WarehouseController();
        defaultListModel = new DefaultListModel<Warehouse>();
        setLayout(new BorderLayout());
        initComponents();
        initEvents();
    }

    private void initComponents(){
        gbc = new GridBagConstraints();
        resetGBC();
        JPanel mainContainer = new JPanel(new GridBagLayout());
        JPanel leftContainer = new JPanel(new GridBagLayout());
        JPanel buttonsContainer = new JPanel(new GridBagLayout());
        //Elements
        list        = new JList();
        input_name  = new JTextField();
        input_min   = new JTextField();
        input_max   = new JTextField();
        btn_new     = new JButton("New plant");
        btn_update  = new JButton("Update plant");
        btn_delete  = new JButton("Delete plant");
        btn_clear   = new JButton("Clear fields");
        products    = new JComboBox<Product>();
        //main container
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        addToPanel(mainContainer, list,          0, 0, 1, 1);
        addToPanel(mainContainer, leftContainer, 1, 0, 1, 1);
        //lefContainer
        resetGBC();
        addToPanel(leftContainer, new JLabel("Plant name"),             0, 0, 1, 1);
        addToPanel(leftContainer, input_name,                               0, 1, 1, 1);
        addToPanel(leftContainer, new JLabel("Plant minimum amount"),   0, 2, 1, 1);
        addToPanel(leftContainer, input_min,                                0, 3, 1, 1);
        addToPanel(leftContainer, new JLabel("Plant maximus amount"),   0, 4, 1, 1);
        addToPanel(leftContainer, input_max,                                0, 5, 1, 1);
        addToPanel(leftContainer, new JLabel("Plant product"),          0, 6, 1, 1);
        addToPanel(leftContainer, products,                                 0, 7, 1, 1);
        addToPanel(leftContainer, buttonsContainer,                         0, 8, 1, 1);
        //buttonsContainer
        addToPanel(buttonsContainer, btn_new,    0, 0, 1, 1);
        addToPanel(buttonsContainer, btn_update, 1, 0, 1, 1);
        addToPanel(buttonsContainer, btn_delete, 2, 0, 1, 1);
        addToPanel(buttonsContainer, btn_clear,  3, 0, 1, 1);


        add(mainContainer, BorderLayout.CENTER);
        //Populate list
        updateList();
        updateProducts();
    }

    private void initEvents(){
        btn_new.addActionListener(e -> registerWarehouse());
        btn_update.addActionListener(e -> updateWarehouse());
        btn_delete.addActionListener(e -> deleteWarehouse());
        btn_clear.addActionListener(e -> clearFields());
        list.addListSelectionListener(e -> updateSelectedWarehouse(list.getSelectedValue()));
    }



    private void registerWarehouse(){
        String name = input_name.getText();
        int min   = Integer.parseInt(input_min.getText());
        int max    = Integer.parseInt(input_max.getText());
        warehouseController.create(new Warehouse(name, min, max, 1));
        Product product;
//        if((product = (Product) products.getSelectedItem()) != null)
//            warehouseController.checkForProductAssign(product.getId(), currentWarehouse.getId());
        updateList();
        clearFields();
    }

    private void updateWarehouse() {
        String name = input_name.getText();
        int min     = Integer.parseInt(input_min.getText());
        int max     = Integer.parseInt(input_max.getText());
        currentWarehouse.setName(name);
        currentWarehouse.setMinProducts(min);
        currentWarehouse.setMaxProducts(max);
        warehouseController.update(currentWarehouse);
        Product product;
        if((product = (Product) products.getSelectedItem()) != null)
            warehouseController.checkForProductAssign(product.getId(), currentWarehouse.getId());
        updateList();
        clearFields();
    }

    private void deleteWarehouse(){
        warehouseController.delete(currentWarehouse.getId());
        clearFields();
        updateList();
        clearFields();
    }
    private void clearFields() {
        input_name.setText("");
        input_min.setText("");
        input_max.setText("");
        currentWarehouse = null;
        updateProducts();
    }

    private void updateSelectedWarehouse(Warehouse warehouse){
        if(warehouse == null) return;
        int id = warehouse.getId();
        String name = warehouse.getName();
        int minProducts = warehouse.getMinProducts();
        int maxProducts = warehouse.getMaxProducts();
        currentWarehouse = new Warehouse(id, name, minProducts, maxProducts, 1);
        input_name.setText(name);
        input_min.setText(String.valueOf(minProducts));
        input_max.setText(String.valueOf(maxProducts));
        updateProducts();
    }

    private void updateList(){
        Iterator<Warehouse> iterator = warehouseController.list().iterator();
        defaultListModel.clear();
        while(iterator.hasNext())
            defaultListModel.addElement(iterator.next());

        list.setModel(defaultListModel);
    }

    private void updateProducts(){
        Iterator<Product> iterator = warehouseController.availableProducts().iterator();
        products.removeAllItems();
        products.setEnabled(true);
        Product product = new Product();
        if(currentWarehouse != null)
            product = warehouseController.getAssignedProduct(currentWarehouse);
        if(product.getId() != -1) {
            products.addItem(product);
            products.setEnabled(false);
        }
        while (iterator.hasNext())
            products.addItem(iterator.next());
    }

    private void addToPanel(JPanel panel, JComponent component, int x, int y, int w, int h){
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = w;
        gbc.gridheight = h;
        panel.add(component, gbc);
    }

    private void resetGBC(){
        gbc.gridy      = 0;
        gbc.gridx      = 0;
        gbc.gridwidth  = 0;
        gbc.gridheight = 0;
        gbc.weightx    = 0;
        gbc.weighty    = 0;
        gbc.ipadx      = 0;
        gbc.ipady      = 0;
        gbc.insets     = new Insets(0, 0, 0, 0);
    }

}
