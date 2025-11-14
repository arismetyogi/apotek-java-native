# Apotek Sehat Sentosa

[API Documentation](./api-doc.md)

## Project Description

Apotek Sehat Sentosa is a pharmacy management system designed to manage medicine inventory and patient information. The application provides a comprehensive solution for managing medicines, including stock levels, expiration dates, and pricing, while also managing patient records.

The system features:

- Medicine inventory management with stock tracking
- Patient information management
- Low stock and near expiry date alerts
- Search functionality for medicines and patients
- Responsive web interface

## Backend

The backend is built with:

- **Language**: Java 21
- **Framework**: Built using native Java HTTP server (com.sun.net.httpserver.HttpServer)
- **Database**: PostgreSQL for data persistence
- **Build Tool**: Docker for containerization
- **API**: RESTful API endpoints for medicines and patients
- **Database Driver**: PostgreSQL JDBC Driver 42.7.8

Key features of the backend:

- Medicine CRUD operations
- Patient CRUD operations
- Stock adjustment functionality
- Low stock and near expiry date reporting
- Pagination support
- CORS-enabled for frontend integration

## Frontend

The frontend is built with:

- **Language**: HTML, CSS, JavaScript (ES6+)
- **Framework**: Vanilla JavaScript (no additional frameworks)
- **Build Tool**: Docker for containerization
- **Styling**: Custom CSS with modern UI design
- **API Integration**: Fetch API for REST communication
- **UI Components**: Responsive tables, forms, and navigation

Key features of the frontend:

- Medicine inventory display with pagination
- Patient management interface
- Search and filter capabilities
- Stock adjustment interface
- Responsive design for various screen sizes
- Modern UI with gradient backgrounds and card-based layout
