import { NextResponse } from "next/server";
import { NextRequest } from "next/server";
import { cookies } from "next/headers";
import { ResponseCookie } from "next/dist/compiled/@edge-runtime/cookies";
import { ReadonlyRequestCookies } from "next/dist/server/web/spec-extension/adapters/request-cookies";

export async function middleware(req) {
  const cookieStore = await cookies();
  const accessToken = cookieStore.get("accessToken")?.value;
  console.log(req);



  const { isLogin, isAccessTokenExpired, accessTokenPayload } =
    parseAccessToken(accessToken);

  if (isLogin && isAccessTokenExpired) {
    await refreshTokens(cookieStore);
  }
//어드민,판매자 구매자
  if (isProtectedRouteAdmin(req.nextUrl.pathname)
  || isProtectedRouteSeller(req.nextUrl.pathname)
  || isProtectedRouteCustomer(req.nextUrl.pathname)) {

    const response = await fetch("http://localhost:8080/api/auth", {
        method: "GET",
        headers: {
            "cookie": cookieStore.toString()
        }
    });

    if (!response.ok) {
        return createUnauthorizedResponse();
        throw new Error("Failed to create estimate request");
    }

    const isAdmin = meResponse.data?.userType === 'Admin';
    const isSeller = meResponse.data?.userType === 'Seller';
    const isCustomer = meResponse.data?.userType === 'Customer';

    if(isProtectedRouteAdmin(req.nextUrl.pathname)){
        if (!isAdmin) {
          return createUnauthorizedResponse();
        }
    }
    if(isProtectedRouteSeller(req.nextUrl.pathname)){
        if (!isAdmin && !Seller) {
          return createUnauthorizedResponse();
        }
    }
    if(isProtectedRouteCustomer(req.nextUrl.pathname)){
        if (!isAdmin && !Customer) {
          return createUnauthorizedResponse();
        }
    }
  return NextResponse.next({
    headers: {
      cookie: cookieStore.toString(),
    },
  });
}
}

function parseAccessToken(accessToken) {
  let isAccessTokenExpired = true;
  let accessTokenPayload = null;

  if (accessToken) {
    try {
      const tokenParts = accessToken.split(".");
      accessTokenPayload = JSON.parse(
        Buffer.from(tokenParts[1], "base64").toString()
      );
      const expTimestamp = accessTokenPayload.exp * 1000;
      isAccessTokenExpired = Date.now() > expTimestamp;
    } catch (e) {
      console.error("토큰 파싱 중 오류 발생:", e);
    }
  }

  const isLogin =
    typeof accessTokenPayload === "object" && accessTokenPayload !== null;

  return { isLogin, isAccessTokenExpired, accessTokenPayload };
}

async function refreshTokens(cookieStore) {
    const meResponse = await fetch("http://localhost:8080/api/auth", {
        method: "GET",
        headers: {
            "cookie": cookieStore.toString()
        }
    });

  const setCookieHeader = meResponse.response.headers.get("Set-Cookie");

  if (setCookieHeader) {
    const cookies = setCookieHeader.split(",");

    for (const cookieStr of cookies) {
      const cookieData = parseCookie(cookieStr);

      if (cookieData) {
        const { name, value, options } = cookieData;
        if (name !== "accessToken" && name !== "apiKey" && name !== "userType") return null;

        cookieStore.set(name, value, options);
      }
    }
  }
}

function parseCookie(cookieStr) {
  const parts = cookieStr.split(";").map((p) => p.trim());
  const [name, value] = parts[0].split("=");

  const options = {};
  for (const part of parts.slice(1)) {
    if (part.toLowerCase() === "httponly") options.httpOnly = true;
    else if (part.toLowerCase() === "secure") options.secure = true;
    else {
      const [key, val] = part.split("=");
      const keyLower = key.toLowerCase();
      if (keyLower === "domain") options.domain = val;
      else if (keyLower === "path") options.path = val;
      else if (keyLower === "max-age") options.maxAge = parseInt(val);
      else if (keyLower === "expires")
        options.expires = new Date(val).getTime();
      else if (keyLower === "samesite"){
        if (sameSiteValue === "lax" || sameSiteValue === "strict" || sameSiteValue === "none") {
            options.sameSite = val.toLowerCase();

      }
    }
  }

  return { name, value, options };
}
}

function isProtectedRouteAdmin(pathname) {
  return (
    pathname.startsWith("/admin/products") ||
    pathname.startsWith("/admin/order")
  );
}
function isProtectedRouteSeller(pathname){
  return (
    pathname.startsWith("/seller")
  );
}
function isProtectedRouteCustomer(pathname){
  return (
    pathname.startsWith("/customer")
  );
}

 
function createUnauthorizedResponse(){
  const loginUrl = new URL('/', process.env.NEXT_PUBLIC_BASE_URL || 'http://localhost:3000');

  return NextResponse.redirect(loginUrl);
}
export const config = {
  // 아래 2가지 경우에는 middleware를 실행하지 않도록 세팅
  // api 로 시작하거나 하는 요청 : /api/~~~
  // 정적 파일 요청 : /~~~.jpg, /~~~.png, /~~~.css, /~~~.js
  // PS. 여기서 말하는 api 로 시작하는 요청은 백엔드 API 서버로의 요청이 아니라 Next.js 고유의 API 서버로의 요청이다.
  // PS. 우리는 현재 이 기능을 사용하고 있지 않다.
  matcher: "/((?!.*\\.|api\\/).*)",
};
