package com.mainproject.be28.cart.mapper;

import com.mainproject.be28.cart.dto.CartDto;
import com.mainproject.be28.cart.entity.Cart;
import com.mainproject.be28.cartItem.dto.CartItemDto;
import com.mainproject.be28.cartItem.entity.CartItem;
import com.mainproject.be28.cartItem.repository.CartItemRepository;
import com.mainproject.be28.member.entity.Member;
import com.mainproject.be28.member.repository.MemberRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
// 빈 카트 조회 시 NullPointException 해결필요
@Component
public class CartMapperImpl implements CartMapper{
    public final CartItemRepository cartItemRepository;
    public final MemberRepository memberRepository;

    public CartMapperImpl(CartItemRepository cartItemRepository, MemberRepository memberRepository) {
        this.cartItemRepository = cartItemRepository;
        this.memberRepository = memberRepository;
    }

    public Cart cartPostDtoToCart(CartDto.Post cartPostDto) {
        if ( cartPostDto == null ) {
            return null;
        }

        Cart cart = new Cart();

        cart.setMember( postToMember( cartPostDto ) );
        cart.setCartItems( cartItemDtoListToCartItemList( cartPostDto.getCartItems() ) );

        return cart;
    }

    public Cart cartPatchDtoToCart(CartDto.Patch cartPatchDto) {
        if ( cartPatchDto == null ) {
            return null;
        }

        Cart cart = new Cart();

        cart.setMember( patchToMember( cartPatchDto ) );
        cart.setCartItems( cartItemDtoListToCartItemList( cartPatchDto.getCartItems() ) );

        return cart;
    }

    protected Member postToMember(CartDto.Post post) {
        if ( post == null ) {
            return null;
        }

        Member member = new Member();

        member.setMemberId( post.getMemberId() );

        return member;
    }

    protected CartItem cartItemDtoToCartItem(CartItemDto cartItemDto) {
        if ( cartItemDto == null ) {
            return null;
        }

        CartItem cartItem = new CartItem();

        cartItem.setCartItemId( cartItemDto.getCartItemId() );
        cartItem.setCount( cartItemDto.getCount() );

        return cartItem;
    }

    protected List<CartItem> cartItemDtoListToCartItemList(List<CartItemDto> list) {
        if ( list == null ) {
            return null;
        }

        List<CartItem> list1 = new ArrayList<CartItem>( list.size() );
        for ( CartItemDto cartItemDto : list ) {
            list1.add( cartItemDtoToCartItem( cartItemDto ) );
        }

        return list1;
    }

    protected Member patchToMember(CartDto.Patch patch) {
        if ( patch == null ) {
            return null;
        }

        Member member = new Member();

        member.setMemberId( patch.getMemberId() );

        return member;
    }
    public CartDto.Response cartToCartResponseDto(Cart cart) {
        List<CartItemDto> cartItemResponseDtos = getCartItemsResponseDto(cart, cart.getMember());
        long price = getTotalPrice(cartItemResponseDtos);
        return new CartDto.Response(cartItemResponseDtos, price);
    }

    public long getTotalPrice(List<CartItemDto> cartItemResponseDtos) {
        long price = 0;
        for(CartItemDto cartItemDto : cartItemResponseDtos){
            price += cartItemDto.getPrice()*cartItemDto.getCount();
        }
        return price;
    }
    public List<CartItemDto> getCartItemsResponseDto(Cart cart,Member member) {
        if(cart == null){
            Cart.createCart(member);
        }
        List<CartItemDto> cartItemDtos = new ArrayList<>();
        List<CartItem> cartItems = cart.getCartItems();
        if (cartItems != null) {
            for (CartItem cartItem : cartItems) {
                if (cartItem.getCount() != 0) {
                    CartItemDto cartItemDto =
                            CartItemDto.builder()
                                    .cartItemId(cartItem.getCartItemId())
                                    .itemId(cartItem.getItem().getItemId())
                                    .count(cartItem.getCount())
                                    .name(cartItem.getItem().getName())
                                    .price(cartItem.getItem().getPrice())
                                    .build();
                    cartItemDtos.add(cartItemDto);
                }
            }
        }
        return cartItemDtos;
    }
}
