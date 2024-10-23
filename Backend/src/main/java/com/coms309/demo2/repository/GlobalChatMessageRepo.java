package com.coms309.demo2.repository;

import com.coms309.demo2.entity.GlobalChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GlobalChatMessageRepo extends JpaRepository<GlobalChatMessage, Long> {
    List<GlobalChatMessage> findAllByOrderByTimestampAsc();
}
