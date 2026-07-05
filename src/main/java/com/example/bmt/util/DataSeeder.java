package com.example.bmt.util;

import com.example.bmt.model.*;
import com.example.bmt.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class DataSeeder implements CommandLineRunner {
    private final CategoryRepository categoryRepo;
    private final ProductRepository productRepo;
    private final UserRepository userRepo;
    private final AddressRepository addressRepo;
    private final CartRepository cartRepo;
    private final CartItemRepository cartItemRepo;
    private final OrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;
    private final PaymentRepository paymentRepo;
    private final ReviewRepository reviewRepo;
    private final AdminRepository adminRepo;
    private final PasswordEncoder encoder;

    public DataSeeder(CategoryRepository c, ProductRepository p, UserRepository u,
                      AddressRepository a, CartRepository cr, CartItemRepository cir,
                      OrderRepository or, OrderItemRepository oir,
                      PaymentRepository pr, ReviewRepository rr, AdminRepository ar, PasswordEncoder e) {
        this.categoryRepo = c; this.productRepo = p; this.userRepo = u;
        this.addressRepo = a; this.cartRepo = cr; this.cartItemRepo = cir;
        this.orderRepo = or; this.orderItemRepo = oir;
        this.paymentRepo = pr; this.reviewRepo = rr; this.adminRepo = ar; this.encoder = e;
    }

    @Override
    public void run(String... args) {
        if (categoryRepo.count() == 0) {
            Category bikes = new Category(); bikes.setName("Bikes"); categoryRepo.save(bikes);
            Category helmets = new Category(); helmets.setName("Helmets"); categoryRepo.save(helmets);

            Product mt15 = new Product();
            mt15.setName("Yamaha MT-15");
            mt15.setDescription("Streetfighter 155cc");
            mt15.setPrice(150000.0); mt15.setStock(10);
            mt15.setImageUrl("/images/mt15.png"); mt15.setCategory(bikes);
            productRepo.save(mt15);

            Product helmet = new Product();
            helmet.setName("Rider Helmet");
            helmet.setDescription("ISI certified full-face helmet");
            helmet.setPrice(2500.0); helmet.setStock(50);
            helmet.setImageUrl("/images/helmet.png"); helmet.setCategory(helmets);
            productRepo.save(helmet);
        }

        if (adminRepo.count() == 0) {
            Admin superAdmin = new Admin();
            superAdmin.setUsername("super");
            superAdmin.setPhone("9876543210");
            superAdmin.setEmail("super@bmt.local");
            superAdmin.setPassword(encoder.encode("super123"));
            superAdmin.setRole(AdminRole.SUPER_ADMIN);
            adminRepo.save(superAdmin);

            Admin admin = new Admin();
            admin.setUsername("admin");
            admin.setPhone("9876500000");
            admin.setEmail("admin@bmt.local");
            admin.setPassword(encoder.encode("admin123"));
            admin.setRole(AdminRole.ADMIN);
            adminRepo.save(admin);
        }

        if (userRepo.count() == 0) {
            User customer = new User();
            customer.setUsername("saga"); customer.setEmail(com.example.bmt.util.EncryptionUtil.encrypt("saga@example.com"));
            customer.setPhone(com.example.bmt.util.EncryptionUtil.encrypt("9876111222"));
            customer.setPassword(encoder.encode("saga123")); customer.setRole("ROLE_CUSTOMER");
            userRepo.save(customer);

            Address addr = new Address();
            addr.setUser(customer);
            addr.setStreet("123 Main Street");
            addr.setCity("Chennai");
            addr.setState("TN");
            addr.setZip("600001");
            addr.setCountry("India");
            addressRepo.save(addr);

            Cart cart = new Cart(); cart.setUser(customer); cartRepo.save(cart);

            Optional<Product> helmetOpt = productRepo.findByNameContainingIgnoreCase("Helmet").stream().findFirst();
            if (helmetOpt.isPresent()) {
                Product helmet = helmetOpt.get();
                CartItem ci = new CartItem(); ci.setCart(cart); ci.setProduct(helmet); ci.setQuantity(2);
                cartItemRepo.save(ci);

                Order order = new Order();
                order.setUser(customer);
                order.setOrderDate(LocalDateTime.now());
                order.setStatus("PAID");
                order.setShippingAddress(addr);
                order.setTotalAmount(helmet.getPrice() * 2);
                orderRepo.save(order);

                OrderItem oi = new OrderItem();
                oi.setOrder(order); oi.setProduct(helmet);
                oi.setQuantity(2); oi.setPrice(helmet.getPrice());
                orderItemRepo.save(oi);

                Payment pay = new Payment();
                pay.setOrder(order);
                pay.setAmount(order.getTotalAmount());
                pay.setMethod("CARD");
                pay.setStatus("SUCCESS");
                pay.setTransactionDate(LocalDateTime.now());
                paymentRepo.save(pay);

                Review review = new Review();
                review.setUser(customer); review.setProduct(helmet);
                review.setRating(5.0);
                review.setComment("Excellent helmet, very comfortable!");
                reviewRepo.save(review);
            }
        }
    }
}
