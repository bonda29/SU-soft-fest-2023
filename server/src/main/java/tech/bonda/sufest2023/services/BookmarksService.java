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

    public ResponseEntity<?> saveBookmark(Integer id, Integer newBookmark) {
        String bookmarks = userRepo.findById(id).get().getBookmarks();

        if (bookmarks == null)
        {
            bookmarks = "" + newBookmark;
        }
        else
        {
            bookmarks += "," + newBookmark;
        }
        userRepo.findById(id).get().setBookmarks(bookmarks);
        return ResponseEntity.ok().body("Bookmarked successfully created");
    }

    public ResponseEntity<?> getBookmarks(Integer id) {
        String bookmarks = userRepo.findById(id).get().getBookmarks();

        if (bookmarks == null)
        {
            return ResponseEntity.badRequest().body("No bookmarks for user with id " + id);
        }
        else
        {
            List<String> bookmarksList = List.of(bookmarks.split(","));
            return ResponseEntity.ok().body(bookmarksList);
        }
    }

    public ResponseEntity<?> deleteBookmark(Integer userId, Integer bookmarkToDelete) {
        // Retrieve the user by their ID
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isEmpty())
        {
            return ResponseEntity.badRequest().body("User with ID " + userId + " does not exist.");
        }

        User user = userOptional.get();
        String bookmarks = user.getBookmarks();

        if (bookmarks == null || bookmarks.isEmpty())
        {
            return ResponseEntity.badRequest().body("No bookmarks for user with ID " + userId);
        }
        else
        {
            List<String> bookmarksList = new ArrayList<>(Arrays.asList(bookmarks.split(",")));
            String bookmarkToDeleteStr = String.valueOf(bookmarkToDelete);

            if (bookmarksList.contains(bookmarkToDeleteStr))
            {
                bookmarksList.remove(bookmarkToDeleteStr);

                // Update the user's bookmarks
                user.setBookmarks(String.join(",", bookmarksList));
                userRepo.save(user);

                return ResponseEntity.ok().body("Bookmark deleted successfully");
            }
            else
            {
                return ResponseEntity.badRequest().body("Bookmark " + bookmarkToDelete + " does not exist for user with ID " + userId);
            }
        }
    }


}
