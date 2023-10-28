package tech.bonda.sufest2023.services;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.bonda.sufest2023.models.User;
import tech.bonda.sufest2023.repository.UserRepo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class BookmarksService {
    private final UserRepo userRepo;

    public ResponseEntity<?> saveBookmark(Integer userId, Integer newBookmark) {
        User user = userRepo.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body("User with ID " + userId + " does not exist.");
        }

        String bookmarks = user.getBookmarks();
        if (bookmarks == null) {
            bookmarks = String.valueOf(newBookmark);
        } else {
            bookmarks += "," + newBookmark;
        }

        user.setBookmarks(bookmarks);
        userRepo.save(user);

        return ResponseEntity.ok().body("Bookmark successfully created");
    }

    public ResponseEntity<?> getBookmarks(Integer userId) {
        User user = userRepo.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body("User with ID " + userId + " does not exist.");
        }

        String bookmarks = user.getBookmarks();
        if (bookmarks == null) {
            return ResponseEntity.ok().body(new ArrayList<>());
        } else {
            List<String> bookmarksList = List.of(bookmarks.split(","));
            return ResponseEntity.ok().body(bookmarksList);
        }
    }

    public ResponseEntity<?> deleteBookmark(Integer userId, Integer bookmarkToDelete) {
        User user = userRepo.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body("User with ID " + userId + " does not exist.");
        }

        String bookmarks = user.getBookmarks();
        if (bookmarks == null || bookmarks.isEmpty()) {
            return ResponseEntity.badRequest().body("No bookmarks for user with ID " + userId);
        }

        List<String> bookmarksList = new ArrayList<>(Arrays.asList(bookmarks.split(",")));
        String bookmarkToDeleteStr = String.valueOf(bookmarkToDelete);

        if (bookmarksList.contains(bookmarkToDeleteStr)) {
            bookmarksList.remove(bookmarkToDeleteStr);

            user.setBookmarks(String.join(",", bookmarksList));
            userRepo.save(user);

            return ResponseEntity.ok().body("Bookmark deleted successfully");
        } else {
            return ResponseEntity.badRequest().body("Bookmark " + bookmarkToDelete + " does not exist for user with ID " + userId);
        }
    }
}
