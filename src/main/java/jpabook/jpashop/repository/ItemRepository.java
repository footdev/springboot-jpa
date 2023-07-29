package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    /**
     * null check를 하는 이유
     * 처음에 save 할 때는 id 값이 존재하지 않는 상태이기 때문에 초기에는 persist()로 영속성 컨텍스트에 저장
     * id값이 있다는 것은 변경 한다는 뜻이기 때문에 update와 비슷한 merge를 통해 원래 값 변경 (추후에 더 설명 예정)
     */
    public void save(Item item) {
        if (item.getId() == null) {
            em.persist(item);
        } else {
            em.merge(item);
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
