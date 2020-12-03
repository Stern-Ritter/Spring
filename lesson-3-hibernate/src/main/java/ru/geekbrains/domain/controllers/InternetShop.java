package ru.geekbrains.domain.controllers;

import ru.geekbrains.persistence.entities.Customer;
import ru.geekbrains.persistence.entities.Product;
import ru.geekbrains.persistence.implementations.DatabaseCustomerServiceImpl;
import ru.geekbrains.persistence.implementations.DatabaseProductServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class InternetShop {

    private DatabaseProductServiceImpl ps;
    private DatabaseCustomerServiceImpl cs;
    private Scanner scanner;

    public InternetShop() {
        ps = new DatabaseProductServiceImpl();
        cs = new DatabaseCustomerServiceImpl();
        scanner = new Scanner(System.in);
    }

    public void startService() {
        showMenu();
        String str;
        while (!(str = scanner.nextLine()).equals("exit")) {
            executeOperation(recognizeOperation(str));
        }
    }

    private void showMenu() {
        System.out.println("Введите одну из следующих команд:");
        System.out.println("customer [id] – вывеси информацию о покупках клиента,");
        System.out.println("product [id] [amount] – вывеси информацию о покупателях, купивших товар,");
        System.out.println("delete_product [id]– удалить товар,");
        System.out.println("delete_customer [id] - удалить покупателя.");
        System.out.println("Для выходы введите exit.");
    }

    private List<String> recognizeOperation(String str) {
        List<String> operation = new ArrayList<>();
        StringTokenizer stok = new StringTokenizer(str, " ");
        while (stok.hasMoreTokens()) {
            operation.add(stok.nextToken());
        }
        return operation;
    }

    private void executeOperation(List<String> operation) {
        try {
            switch (operation.get(0)) {
                case "customer":
                    findAllPurchasesOfCustomer(Long.parseLong(operation.get(1)));
                    break;
                case "product":
                    findAllCustomersOfProduct(Long.parseLong(operation.get(1)));
                    break;
                case "delete_customer":
                    deleteCustomer(Long.parseLong(operation.get(1)));
                    break;
                case "delete_product":
                    deleteProduct(Long.parseLong(operation.get(1)));
                    break;
                default:
                    System.out.println("Некорректная комманда.");
            }
        } catch (RuntimeException ex) {
            System.out.println("Ошибка при выполнении команды.");
        }
    }

    private void findAllPurchasesOfCustomer(Long id) {
        List<Product> products = cs.findAllPurchases(id);
        System.out.printf("Покупатель #%d приобрел:\n", id);
        for (Product product : products) {
            System.out.println(product);
        }
    }

    private void findAllCustomersOfProduct(Long id) {
        List<Customer> customers = ps.findAllCustomers(id);
        System.out.printf("Товар #%d приобрели:\n", id);
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    private void deleteProduct(Long id) {
        ps.delete(id);
        System.out.printf("Товар #%d успешно удален.\n", id);
    }

    private void deleteCustomer(Long id) {
        cs.delete(id);
        System.out.printf("Покупатель #%d успешно удален.\n", id);
    }

    public void generateValues() {
        Customer firstCustomer = new Customer(null, "Евгений", "Лебедев", null);
        Customer secondCustomer = new Customer(null, "Екатерина", "Медведева", null);
        Customer thirdCustomer = new Customer(null, "Артем", "Орлов", null);

        cs.insert(firstCustomer);
        cs.insert(secondCustomer);
        cs.insert(thirdCustomer);

        Customer first = cs.getEntityById(firstCustomer.getId());
        Customer second = cs.getEntityById(secondCustomer.getId());
        Customer third = cs.getEntityById(thirdCustomer.getId());

        Product firstProduct = new Product(null, "iPhone", "super phone", new BigDecimal(999),
                new ArrayList<>(Arrays.asList(first, second)));
        Product secondProduct = new Product(null, "MacBook", "super laptop", new BigDecimal(1999),
                new ArrayList<>(Arrays.asList(second, third)));
        Product thirdProduct = new Product(null, "iPad", "super tablet", new BigDecimal(1199),
                new ArrayList<>(Arrays.asList(third)));
        Product fourthProduct = new Product(null, "iWatch", "super watch", new BigDecimal(399),
                new ArrayList<>(Arrays.asList(first, third)));

        ps.insert(firstProduct);
        ps.insert(secondProduct);
        ps.insert(thirdProduct);
        ps.insert(fourthProduct);
    }
}