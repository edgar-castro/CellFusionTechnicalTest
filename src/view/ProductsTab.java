package view;

import controller.ProductController;
import model.Product;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

public class ProductsTab extends JPanel {

    private Product currentProduct;
    private ProductController productController;
    private DefaultListModel<Product> defaultListModel;
    private JList list;
    private JButton btn_new, btn_update, btn_delete, btn_clear;
    private JTextField input_name, input_total, input_sold;
    private GridBagConstraints gbc;

    public ProductsTab() {
        productController = new ProductController();
        defaultListModel = new DefaultListModel<Product>();
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
        input_total = new JTextField();
        input_sold  = new JTextField();
        btn_new     = new JButton("New product");
        btn_update  = new JButton("Update product");
        btn_delete  = new JButton("Delete product");
        btn_clear   = new JButton("Clear fields");
        //main container
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        addToPanel(mainContainer, list,          0, 0, 1, 1);
        addToPanel(mainContainer, leftContainer, 1, 0, 1, 1);
        //lefContainer
        resetGBC();
        addToPanel(leftContainer, new JLabel("Product name"),           0, 0, 1, 1);
        addToPanel(leftContainer, input_name,                               0, 1, 1, 1);
        addToPanel(leftContainer, new JLabel("Product total amount"),   0, 2, 1, 1);
        addToPanel(leftContainer, input_total,                              0, 3, 1, 1);
        addToPanel(leftContainer, new JLabel("Product sold amount"),    0, 4, 1, 1);
        addToPanel(leftContainer, input_sold,                               0, 5, 1, 1);
        addToPanel(leftContainer, buttonsContainer,                         0, 6, 1, 1);
        //buttonsContainer
        addToPanel(buttonsContainer, btn_new,    0, 0, 1, 1);
        addToPanel(buttonsContainer, btn_update, 1, 0, 1, 1);
        addToPanel(buttonsContainer, btn_delete, 2, 0, 1, 1);
        addToPanel(buttonsContainer, btn_clear,  3, 0, 1, 1);


        add(mainContainer, BorderLayout.CENTER);
        //Populate list
        updateList();
    }

    private void initEvents(){
        btn_new.addActionListener(e -> registerProduct());
        btn_update.addActionListener(e -> updateProduct());
        btn_delete.addActionListener(e -> deleteProduct());
        btn_clear.addActionListener(e -> clearFields());
        list.addListSelectionListener(e -> updateSelectedProduct((Product)list.getSelectedValue()));
    }



    private void registerProduct(){
        String name = input_name.getText();
        int total   = Integer.parseInt(input_total.getText());
        int sold    = Integer.parseInt(input_sold.getText());
        productController.create(new Product(name, total, sold));
        updateList();
        clearFields();
    }

    private void updateProduct() {
        String name = input_name.getText();
        int total   = Integer.parseInt(input_total.getText());
        int sold    = Integer.parseInt(input_sold.getText());
        currentProduct.setName(name);
        currentProduct.setTotal(total);
        currentProduct.setSold(sold);
        productController.update(currentProduct);
        updateList();
        clearFields();
    }

    private void deleteProduct(){
        productController.delete(currentProduct.getId());
        clearFields();
        updateList();
        clearFields();
    }
    private void clearFields() {
        input_name.setText("");
        input_total.setText("");
        input_sold.setText("");
    }

    private void updateSelectedProduct(Product product){
        if(product == null) return;
        int id = product.getId();
        String name = product.getName();
        int total = product.getTotal();
        int sold = product.getSold();
        currentProduct = new Product(id, name, total, sold);
        input_name.setText(name);
        input_total.setText(String.valueOf(total));
        input_sold.setText(String.valueOf(sold));
        System.out.println(currentProduct.getName());
    }

    //Can avoid so many call to update list having the selected list item index and only update the model list item?
    private void updateList(){
        Iterator<Product> iterator = productController.list().iterator();
        defaultListModel.clear();
        while(iterator.hasNext()){
            defaultListModel.addElement(iterator.next());
        }
        list.setModel(defaultListModel);
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
