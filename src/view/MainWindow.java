package view;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class MainWindow extends JFrame {

    private JPanel dashboardTab, productsTab, warehouseTab;
    private JTabbedPane tabs;

    public MainWindow(String title) {
        super(title);
        setSize(720, 480);
        initComponents();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initComponents() {
        getContentPane().setLayout(new BorderLayout());
        tabs = new JTabbedPane();
        dashboardTab = new DashboardTab();
        productsTab = new ProductsTab();
        warehouseTab = new WarehouseTab();
        tabs.add(dashboardTab, "Dashboard");
        tabs.add(productsTab, "Products");
        tabs.add(warehouseTab, "Warehouse");
        add(tabs);
    }
}
