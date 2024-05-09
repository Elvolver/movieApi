package com.movieflix.movieApi.controllers;

//@RestController
//@RequestMapping("/forgotPassword")
public class ForgotPasswordController {

//    private final UserRepository userRepository;
//    private final EmailService emailService;
//    private final ForgotPasswordRepository forgotPasswordRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    public ForgotPasswordController(UserRepository userRepository, EmailService emailService, ForgotPasswordRepository forgotPasswordRepository, PasswordEncoder passwordEncoder) {
//        this.userRepository = userRepository;
//        this.emailService = emailService;
//        this.forgotPasswordRepository = forgotPasswordRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    //send mail for email verification
//    @PostMapping("/verifyMail")
//    public ResponseEntity<String> verifyEmail(@RequestBody Email e) {
//        String email = e.email();
//        System.out.println(email);
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("Please provide a valid user email"));
//
//        int otp = otpGenerator();
//
//        MailBody mailBody = MailBody.builder()
//                .to(email)
//                .text("This is the OTP for your forgot password request: " + otp)
//                .subject("OTP for forgot password request")
//                .build();
//
//        ForgotPassword forgotPassword = ForgotPassword.builder()
//                .otp(otp)
//                //TODO
//                .expirationDate(new Date(System.currentTimeMillis() + 300 * 1000))
//                .user(user)
//                .build();
//
//        emailService.sendSimpleMessage(mailBody);
//
//        ForgotPassword oldForgotPassword = forgotPasswordRepository.findByUser(user);
//        if (oldForgotPassword != null) {
//            forgotPasswordRepository.delete(oldForgotPassword);
//        }
//        ForgotPassword save = forgotPasswordRepository.save(forgotPassword);
//
//        return ResponseEntity.ok("email sent for verification");
//    }
//
//    @PostMapping("/changePassword")
//    public ResponseEntity<String> changePassword(@RequestBody ChangePassword changePassword) {
//
//        String email = changePassword.email();
//        Integer otp = changePassword.otp();
//
//        System.out.println(changePassword);
//
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("Please provide a valid user email"));
//
//        ForgotPassword forgotPassword = forgotPasswordRepository.findByOtpAndUser(otp, user)
//                .orElseThrow(() -> new RuntimeException("Please provide a valid user email: " + email));
//
//        if (forgotPassword.getExpirationDate().before(Date.from(Instant.now()))) {
//            System.out.println("deleting #" + forgotPassword.getId());
//            forgotPasswordRepository.deleteById(forgotPassword.getId());
//            return new ResponseEntity<>("OTP is expired!", HttpStatus.EXPECTATION_FAILED);
//        }
//
//        if (!Objects.equals(changePassword.newPassword(), changePassword.confirmPassword())) {
//            return new ResponseEntity<>("Please enter the password again!", HttpStatus.EXPECTATION_FAILED);
//        }
//
//        String encodedPassword = passwordEncoder.encode(changePassword.newPassword());
//
//        userRepository.updatePassword(email, encodedPassword);
//        forgotPasswordRepository.deleteById(forgotPassword.getId());
//
//        return ResponseEntity.ok("Password has been changed!");
//    }
//
//    private Integer otpGenerator() {
//        Random random = new Random();
//        return random.nextInt(100_000, 999_999);
//    }
}
