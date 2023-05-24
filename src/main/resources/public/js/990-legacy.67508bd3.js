"use strict";(self["webpackChunkhache"]=self["webpackChunkhache"]||[]).push([[990],{8990:(e,t,n)=>{n.r(t),n.d(t,{createSwipeBackGesture:()=>s});var r=n(6587),a=n(545),c=n(6515);
/*!
 * (C) Ionic http://ionicframework.com - MIT License
 */
const s=(e,t,n,s,i)=>{const o=e.ownerDocument.defaultView;let h=(0,a.i)(e);const u=e=>{const t=50,{startX:n}=e;return h?n>=o.innerWidth-t:n<=t},l=e=>h?-e.deltaX:e.deltaX,d=e=>h?-e.velocityX:e.velocityX,k=n=>(h=(0,a.i)(e),u(n)&&t()),w=e=>{const t=l(e),n=t/o.innerWidth;s(n)},p=e=>{const t=l(e),n=o.innerWidth,a=t/n,c=d(e),s=n/2,h=c>=0&&(c>.2||t>s),u=h?1-a:a,k=u*n;let w=0;if(k>5){const e=k/Math.abs(c);w=Math.min(e,540)}i(h,a<=0?.01:(0,r.m)(0,a,.9999),w)};return(0,c.createGesture)({el:e,gestureName:"goback-swipe",gesturePriority:40,threshold:10,canStart:k,onStart:n,onMove:w,onEnd:p})}}}]);
//# sourceMappingURL=990-legacy.67508bd3.js.map