# Test Inventory System – Cell Fusion
## How to use:
### Dashboard Views
The dashboard view have two components, the table and a button. When you start the app for the first time, the table is empty because the system don't have registered relations between products and plants. Once relations registered, you can use the **Update** button to update the table and see the information. You also need to press the button after do an action that modifies the relations.
### Products View
In the products tab you can see, create, update and delete the products stored in the database. The view is build by two main parts, the list of elements (products) and the form.
Clicking in any element of the list, we can update or delete, or we can create a new product.
### Warehouse view
The warehouse view is a little similar to the products view, the only significant change is that we have a combo box where is displayed all product that aren't assigned to a warehouse, in that way you can select and assign it.
**You can't assign a product when you create, only can on update.**

##### SQL:
The stock status is updated by triggers on insert and update for the warehouse_plant_product table, and for update in the table product. Is updated by cascade on delete for product and warehouse_plant.

## Not covered cases:
-	No input validation in Text Fields
-	No disable update and delete button when no item is selected


 
 
