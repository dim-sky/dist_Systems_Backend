//package hua.it21891_backend.config;
//
//import hua.it21891_backend.entities.User;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.authorization.AuthorizationDecision;
//import org.springframework.security.authorization.AuthorizationManager;
//import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
//import org.springframework.security.core.Authentication;
//
//import java.util.function.Supplier;
//
//public class CustomAuthenticatedUsersCheck implements AuthorizationManager<RequestAuthorizationContext> {
//    @Override
//    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
//        Authentication auth = authentication.get();
//        if (auth == null) {
//            return new AuthorizationDecision(false);
//        }
//        User user = (User) auth.getPrincipal();
//
//        if (!user.isAuthenticated()){
//            throw new AccessDeniedException("Ο λογαριασμός του χρήστη δεν έχει επιβεβαιωθεί! Περιμένετε μέχρι ένας admin να ολοκληρώσει την διαδικάσία εγγραφής σας");
//        }
//
//        return new AuthorizationDecision(user.isAuthenticated());
//    }
//
//}