package view;

import controller.DashboardController;
import model.Dashboard;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Iterator;
import java.util.List;

public class DashboardTab extends JPanel {

    private JTable table;
    private List<Dashboard> list;
    private DashboardController dashboardController;


    private DefaultTableModel tableModel;

    public DashboardTab() {
        dashboardController = new DashboardController();
        initComponents();
        updateTable();
    }


    private void initComponents() {
        setLayout(new BorderLayout());
        table = new JTable();
        JButton btn_update = new JButton("Update");
        btn_update.addActionListener(e -> updateTable());
        add(btn_update, BorderLayout.SOUTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

    }


    public void updateTable() {
        tableModel = (DefaultTableModel) table.getModel();
        tableModel.setRowCount(0);
        String [] headers = {"Product Name", "On Stock", "Sold", "Total", "Warehouse"};
        tableModel.setColumnIdentifiers(headers);

        list = dashboardController.list();
        Iterator<Dashboard> iterator = list.iterator();
        while (iterator.hasNext()){
            Dashboard d = iterator.next();
            String [] rowData = {
                    d.getProductName(),
                    String.valueOf(d.getOnStock()),
                    String.valueOf(d.getTotal()),
                    String.valueOf(d.getSold()),
                    d.getWarehouseName()
            };
            tableModel.addRow(rowData);
        }
        updateColor();
    }

    private void updateColor() {
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                Color color = Color.decode(list.get(row).getColor());
                label.setBackground(color);
                return label;
            }
        });
    }

}