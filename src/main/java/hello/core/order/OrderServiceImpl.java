package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;

public class OrderServiceImpl implements OrderService {

    /**
     * private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
     * -> private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
     *
     * 할인 정책 변경시 FixDiscountPolicy -> RateDiscountPolicy 로 수정
     * OCP 위반
     * 
     * OrderServiceImpl는 인터페이스인 DiscountPolicy와 구현체인 FixDiscountPolicy, RateDiscountPolicy 에도 함께 의존한다
     * DIP 위반 -> 추상(인터페이스)에만 의존하도록 변경해야 한다
     *
     * 해결방안:: OrderServiceImpl에 DiscountPolicy의 구현 객체를 대신 생성하고 주입해줘야한다.
     */
//    private final MemberRepository memberRepository = new MemoryMemberRepository();
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
