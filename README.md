# Thesis Project

## Overview  

This repository contains the source code for an **E-Shop Management System**, developed as part of my thesis project. The thesis focuses on comparing **Modular Monolith** and **Microservices Architectures** in web application development. This project has been implemented using **Spring Boot** and **MongoDB** to explore the advantages and challenges of both architectural patterns.  

## 📌 E-Shop Management Overview  

The E-Shop Management System is an online store that allows users to browse products, place orders, and track their order status. Additionally, administrators have full control over products and orders.  

## 🎯 Features  

#### ✅ User Functionality  
- View all available products  
- Get details of a specific product  
- Place an order  
- Check the status of an order (pending or completed)  

#### 🛠️ Admin Functionality  
- Add a new product  
- Update product details  
- Delete a product  
- View all orders  
- Mark an order as completed  

---

## 📡 API Endpoints  

| **HTTP Method** | **Endpoint** | **Description** |
|---------------|----------------------------|------------------|
| **GET** | `/eshop/products` | Retrieve all available products |
| **GET** | `/eshop/product/{id}` | Get details of a specific product by ID |
| **POST** | `/eshop-management/new-product` | Add a new product |
| **PUT** | `/eshop-management/update-product/{id}` | Update product details |
| **DELETE** | `/eshop-management/delete-product/{id}` | Delete a product |
| **GET** | `/eshop-management/orders` | Retrieve all orders |
| **GET** | `/eshop/order/{id}` | Get details of an order by ID |
| **POST** | `/eshop/place-order` | Place a new order |
| **PUT** | `/eshop-management/orders/completed/{id}` | Mark an order as completed |

---

## 🏗️ Technologies Used  

- **Spring Boot** – Backend framework
- **Spring Modulith** – Modular Monolith support for better modularization  
- **MongoDB** – NoSQL database  
- **Java** – Main programming language  
- **REST API** – Communication between frontend & backend  

---

## 📜 License  

This project was developed as part of my **thesis research** on **Modular Monolith vs Microservices Architecture** and is open for **educational and research purposes**. 
