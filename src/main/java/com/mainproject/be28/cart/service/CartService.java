package com.mainproject.be28.cart.service;

import com.mainproject.be28.cart.entity.Cart;
import com.mainproject.be28.cart.repository.CartRepository;
import com.mainproject.be28.cartItem.dto.CartItemDto;
import com.mainproject.be28.cartItem.entity.CartItem;
import com.mainproject.be28.exception.BusinessLogicException;
import com.mainproject.be28.exception.ExceptionCode;
import com.mainproject.be28.item.entity.Item;
import com.mainproject.be28.item.repository.ItemRepository;
import com.mainproject.be28.item.service.ItemService;
import com.mainproject.be28.member.entity.Member;
import com.mainproject.be28.member.repository.MemberRepository;
import com.mainproject.be28.member.service.MemberService;
import com.mainproject.be28.utils.CustomBeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;
    private final ItemService itemService;
    private final CustomBeanUtils<Cart> beanUtils;

    public CartService(MemberService memberService
            , MemberRepository memberRepository,CartRepository cartRepository, ItemRepository itemRepository
            , ItemService itemService, CustomBeanUtils<Cart> beanUtils) {
        this.memberService = memberService;
        this.memberRepository = memberRepository;
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
        this.itemService = itemService;
        this.beanUtils = beanUtils;
    }

    public void addCart(CartItemDto cartItemDto, long memberId) {
//       Member member = memberRepository.findById(memberId);
//        Cart cart = cartRepository.findByMemberId(member.getMemberId());
//
//
//        if (cart == null) {
//            cart = Cart.createCart(member);
//            cartRepository.save(cart);
//        }
//
//        Item item = itemRepository.findById(cartItemDto.getItemId()).orElseThrow(() -> new BusinessLogicException(ExceptionCode.ITEM_NOT_FOUND));
//        CartItem cartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId());
//
//        // 해당 상품이 장바구니에 존재하지 않는다면 생성 후 추가
//        if (cartItem == null) {
//            cartItem = CartItem.createCartItem(cart, item, cartItemDto.getCount());
//            cartItemRepository.save(cartItem);
//
//            // 해당 상품이 장바구니에 이미 존재한다면 수량을 증가
//        } else {
//            cartItem.addCount(cartItemDto.getCount());
//        }
////        return cartItem.getCartItemId();
//    }
    }
    public Cart updateCart(Cart cart) {
        Cart findCart = findCart(cart.getCartId());
        findCart.setModifiedAt(LocalDateTime.now());
        // 회원 본인만 수정 권한 기능 추가 필요
        Cart updatedCart =
                beanUtils.copyNonNullProperties(cart, findCart);
        return cartRepository.save(updatedCart);
    }

    private Cart findCart(Long cartId) {
        Optional<Cart> cart = cartRepository.findById(cartId);
        return cart.orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOUND));
    }

//    @Transactional(readOnly = true)
//    public List<CartItemDto> getCartList(String email) {

//        Member member = memberRepository.findByEmail(email);
//        Cart cart = cartRepository.findByMemberId(member.getMemberId());
//
//        List<CartItemDto> cartListDtos  = cart==null? null:cartItemRepository.findCartListDto(cart.getCartId());
//        return cartListDtos;
//    }

    private void deleteCart(long cartId) {
        Cart cart = findCart(cartId);
        cartRepository.delete(cart);
    }
// 로그인한 회원이 갖고 있는 장바구니 모두 삭제 기
//    private void deleteCarts(Long memberId) {
//        cartRepository.deleteAllByMemberId(memberId);
//    }

    private long totalPrice(Cart cart) {
        long price =0;
        List<CartItem> cartItems = cart.getCartItems();
        for(CartItem item : cartItems){
            price += item.getCount()*item.getItem().getPrice();
        }
        return price;
    }
}
