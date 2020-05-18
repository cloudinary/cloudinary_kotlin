package com.cloudinary

import org.junit.Test


class SanityTests {
    val cloudinary = Cloudinary()

    @Test
    fun testSanity() {

//// -----------------------
//
//// $award_!First!
//cloudinary.url {
//publicId("sample")
//variable("award", "First")
//}.generate()
//
//// -----------------------
//
//// $bcolor_!rgb:0000ff!/if_fc_gt_0,$bcolor_!red!/bo_5px_solid_$bcolor,c_fill,h_120,w_120
//cloudinary.url {
//publicId("sample")
//variable("bcolor", "rgb:0000ff")
//ifCondition("fc > 0")
//variable("bcolor", "red")
//endIfCondition()
//border(Border.solid() {
//width(5)
//color("\$bcolor")
//})
//resize(Resize.fill(120, 120))
//}.generate()
//
//// -----------------------
//
//// $img_current,$height_ih/if_w_lt_800/c_scale,e_blur:800,u_$img,w_800,h_$height/e_brightness:40,fl_layer_apply/if_end
//cloudinary.url {
//publicId("sample")
//variable("img", "current")
//variable("height", "ih")
//ifCondition("w < 800")
//underlay(Layer.image("\$img") {
//effect(Effect.blur(800))
//resize(Resize.scale(800, "\$height"))
//adjust(Adjust.brightness(40))
//})
//endIfCondition()
//}.generate()
//
//// -----------------------
//
//// $img_current/e_grayscale/l_$img,w_0.3,bo_10px_solid_white/fl_layer_apply,g_north_west/q_auto,f_auto,dpr_2.0,w_900
//cloudinary.url {
//publicId("sample")
//variable("img", "current")
//effect(Effect.grayscale())
//overlay(Layer.image("\$img") {
//border(Border.solid() {
//width(10)
//color(Color.WHITE)
//})
//resize(Resize.scale(0.3))
//}, Position.northWest())
//resize(Resize.scale(900))
//format(Format.auto())
//quality(Quality.auto())
//dpr(2.0)
//}.generate()
//
//// -----------------------
//
//// $mainvideowidth_500/$overlaywidth_$mainvideowidth_div_3/w_$mainvideowidth/l_video:kitten_fighting,w_$overlaywidth,g_south_east
//cloudinary.url {
//publicId("sample")
//variable("mainvideowidth", 500)
//variable("overlaywidth", "\$mainvideowidth / 3")
//resize(Resize.scale("\$mainvideowidth"))
//overlay(Layer.video("kitten_fighting") {
//resize(Resize.scale("\$overlaywidth") {
//gravity(Gravity.southEast())
//})
//})
//}.generate()
//
//// -----------------------
//
//// $newwidth_10_add_iw_mul_0.3,w_$newwidth
//cloudinary.url {
//publicId("sample")
//variable("newwidth", "10 + iw * 0.3")
//resize(Resize.scale("\$newwidth"))
//}.generate()
//
//// -----------------------
//
//// $qualtag_!Pro!/if_$qualtag_in_tags,q_80/if_else,q_30/w_500
//cloudinary.url {
//publicId("sample")
//variable("qualtag", "Pro")
//ifCondition("\$qualtag in tags")
//quality(80)
//ifElse()
//quality(30)
//endIfCondition()
//resize(Resize.scale(500))
//}.generate()
//
//// -----------------------
//
//// $qualtag_!Pro!/if_$qualtag_in_tags,q_90/if_else,q_30/w_500
//cloudinary.url {
//publicId("sample")
//variable("qualtag", "Pro")
//ifCondition("\$qualtag in tags")
//quality(90)
//ifElse()
//quality(30)
//endIfCondition()
//resize(Resize.scale(500))
//}.generate()
//
//// -----------------------
//
//// $qualtag_!important!,$delwidth_450/t_highqual-if-tagged
//cloudinary.url {
//publicId("sample")
//variable("qualtag", "important")
//variable("delwidth", 450)
//namedTransformation("highqual-if-tagged")
//}.generate()
//
//// -----------------------
//
//// $rating_!thumbs-down!/t_image_ratings
//cloudinary.url {
//publicId("sample")
//variable("rating", "thumbs-down")
//namedTransformation("image_ratings")
//}.generate()
//
//// -----------------------
//
//// $rating_!thumbs-down!/t_image_ratings/w_200
//cloudinary.url {
//publicId("sample")
//variable("rating", "thumbs-down")
//namedTransformation("image_ratings")
//resize(Resize.scale(200))
//}.generate()
//
//// -----------------------
//
//// $rating_!thumbs-up!/t_image_ratings
//cloudinary.url {
//publicId("sample")
//variable("rating", "thumbs-up")
//namedTransformation("image_ratings")
//}.generate()
//
//// -----------------------
//
//// $rating_!thumbs-up!/t_image_ratings/w_200
//cloudinary.url {
//publicId("sample")
//variable("rating", "thumbs-up")
//namedTransformation("image_ratings")
//resize(Resize.scale(200))
//}.generate()
//
//// -----------------------
//
//// $w_200,$ar_0.8/w_$w,ar_$ar,c_fill,g_face
//cloudinary.url {
//publicId("sample")
//variable("w", 200)
//variable("ar", 0.8)
//resize(Resize.fill("\$w") {
//aspectRatio("\$ar")
//gravity(Gravity.face())
//})
//}.generate()
//
//// -----------------------
//
//// $width_115/t_passport_photo
//cloudinary.url {
//publicId("sample")
//variable("width", 115)
//namedTransformation("passport_photo")
//}.generate()
//
//// -----------------------
//
//// $width_140/t_passport_photo
//cloudinary.url {
//publicId("sample")
//variable("width", 140)
//namedTransformation("passport_photo")
//}.generate()
//
//// -----------------------
//
//// $width_165/t_passport_photo
//cloudinary.url {
//publicId("sample")
//variable("width", 165)
//namedTransformation("passport_photo")
//}.generate()
//
//// -----------------------
//
//// a_180/w_300,h_200,c_fill/l_video:dog,fl_splice,w_300,h_200,c_fill,so_0,du_5/fl_layer_apply
//cloudinary.url {
//publicId("sample")
//rotate(Rotate.angle(180))
//resize(Resize.fill(300, 200))
//concatenate(Layer.video("dog") {
//trim(Trim.range(0) {
//duration(5)
//})
//resize(Resize.fill(300, 200))
//})
//}.generate()
//
//// -----------------------
//
//// a_20
//cloudinary.url {
//publicId("sample")
//rotate(Rotate.angle(20))
//}.generate()
//
//// -----------------------
//
//// a_90
//cloudinary.url {
//publicId("sample")
//rotate(Rotate.angle(90))
//}.generate()
//
//// -----------------------
//
//// ac_none
//cloudinary.url {
//publicId("sample")
//transcode(AudioCodec.NONE)
//}.generate()
//
//// -----------------------
//
//// af_22050
//cloudinary.url {
//publicId("sample")
//transcode(AudioFrequency.AF22050)
//}.generate()
//
//// -----------------------
//
//// ar_16:9,c_fill
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill() {
//aspectRatio("16:9")
//})
//}.generate()
//
//// -----------------------
//
//// ar_16:9,c_fill/c_scale,w_auto/c_limit,w_1000
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill() {
//aspectRatio("16:9")
//})
//resize(Resize.scale("auto"))
//resize(Resize.limitFit(1000))
//}.generate()
//
//// -----------------------
//
//// ar_16:9,c_fill/c_scale,w_auto:breakpoints
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill() {
//aspectRatio("16:9")
//})
//resize(Resize.scale("auto:breakpoints"))
//}.generate()
//
//// -----------------------
//
//// ar_16:9,c_fill/c_scale,w_auto:breakpoints:json
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill() {
//aspectRatio("16:9")
//})
//resize(Resize.scale("auto:breakpoints:json"))
//}.generate()
//
//// -----------------------
//
//// ar_16:9,c_fill/c_scale,w_auto:breakpoints_200_1920_30_15
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill() {
//aspectRatio("16:9")
//})
//resize(Resize.scale("auto:breakpoints_200_1920_30_15"))
//}.generate()
//
//// -----------------------
//
//// ar_2.5,c_crop
//cloudinary.url {
//publicId("sample")
//resize(Resize.crop() {
//aspectRatio(2.5)
//})
//}.generate()
//
//// -----------------------
//
//// ar_4:3,c_fill/c_scale,w_auto,dpr_auto
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill() {
//aspectRatio("4:3")
//})
//resize(Resize.scale("auto"))
//dpr(Dpr.auto())
//}.generate()
//
//// -----------------------
//
//// b_blue,c_pad,h_400,w_660
//cloudinary.url {
//publicId("sample")
//resize(Resize.pad(660, 400) {
//background(Color.BLUE)
//})
//}.generate()
//
//// -----------------------
//
//// bo_1px_solid_black,a_0/f_auto,q_auto,dpr_auto
//cloudinary.url {
//publicId("sample")
//border(Border.solid() {
//width(1)
//color(Color.BLACK)
//})
//rotate(Rotate.angle(0))
//format(Format.auto())
//quality(Quality.auto())
//dpr(Dpr.auto())
//}.generate()
//
//// -----------------------
//
//// bo_1px_solid_black,a_0/f_auto,q_auto,dpr_auto,h_300
//cloudinary.url {
//publicId("sample")
//border(Border.solid() {
//width(1)
//color(Color.BLACK)
//})
//rotate(Rotate.angle(0))
//resize(Resize.scale() {
//height(300)
//})
//format(Format.auto())
//quality(Quality.auto())
//dpr(Dpr.auto())
//}.generate()
//
//// -----------------------
//
//// bo_1px_solid_black,dpr_2,q_auto,f_auto,w_400
//cloudinary.url {
//publicId("sample")
//border(Border.solid() {
//width(1)
//color(Color.BLACK)
//})
//resize(Resize.scale(400))
//format(Format.auto())
//quality(Quality.auto())
//dpr(2)
//}.generate()
//
//// -----------------------
//
//// bo_1px_solid_black,q_auto,dpr_2,f_auto
//cloudinary.url {
//publicId("sample")
//border(Border.solid() {
//width(1)
//color(Color.BLACK)
//})
//format(Format.auto())
//quality(Quality.auto())
//dpr(2)
//}.generate()
//
//// -----------------------
//
//// bo_1px_solid_gray
//cloudinary.url {
//publicId("sample")
//border(Border.solid() {
//width(1)
//color(Color.GRAY)
//})
//}.generate()
//
//// -----------------------
//
//// bo_1px_solid_gray,dpr_auto
//cloudinary.url {
//publicId("sample")
//border(Border.solid() {
//width(1)
//color(Color.GRAY)
//})
//dpr(Dpr.auto())
//}.generate()
//
//// -----------------------
//
//// bo_1px_solid_gray/dpr_auto
//cloudinary.url {
//publicId("sample")
//border(Border.solid() {
//width(1)
//color(Color.GRAY)
//})
//dpr(Dpr.auto())
//}.generate()
//
//// -----------------------
//
//// bo_1px_solid_gray/f_auto,dpr_auto,q_auto
//cloudinary.url {
//publicId("sample")
//border(Border.solid() {
//width(1)
//color(Color.GRAY)
//})
//format(Format.auto())
//quality(Quality.auto())
//dpr(Dpr.auto())
//}.generate()
//
//// -----------------------
//
//// bo_1px_solid_gray/f_auto,q_auto,dpr_auto
//cloudinary.url {
//publicId("sample")
//border(Border.solid() {
//width(1)
//color(Color.GRAY)
//})
//format(Format.auto())
//quality(Quality.auto())
//dpr(Dpr.auto())
//}.generate()
//
//// -----------------------
//
//// bo_1px_solid_gray/f_auto,w_650,dpr_2,q_auto
//cloudinary.url {
//publicId("sample")
//border(Border.solid() {
//width(1)
//color(Color.GRAY)
//})
//resize(Resize.scale(650))
//format(Format.auto())
//quality(Quality.auto())
//dpr(2)
//}.generate()
//
//// -----------------------
//
//// bo_1px_solid_gray/q_auto,h_450,f_auto,dpr_2
//cloudinary.url {
//publicId("sample")
//border(Border.solid() {
//width(1)
//color(Color.GRAY)
//})
//resize(Resize.scale() {
//height(450)
//})
//format(Format.auto())
//quality(Quality.auto())
//dpr(2)
//}.generate()
//
//// -----------------------
//
//// bo_1px_solid_gray/q_auto,w_400,f_auto,dpr_2
//cloudinary.url {
//publicId("sample")
//border(Border.solid() {
//width(1)
//color(Color.GRAY)
//})
//resize(Resize.scale(400))
//format(Format.auto())
//quality(Quality.auto())
//dpr(2)
//}.generate()
//
//// -----------------------
//
//// bo_1px_solid_gray/w_300,f_auto,q_auto,dpr_2
//cloudinary.url {
//publicId("sample")
//border(Border.solid() {
//width(1)
//color(Color.GRAY)
//})
//resize(Resize.scale(300))
//format(Format.auto())
//quality(Quality.auto())
//dpr(2)
//}.generate()
//
//// -----------------------
//
//// bo_1px_solid_gray/w_400,f_auto,q_auto,dpr_2
//cloudinary.url {
//publicId("sample")
//border(Border.solid() {
//width(1)
//color(Color.GRAY)
//})
//resize(Resize.scale(400))
//format(Format.auto())
//quality(Quality.auto())
//dpr(2)
//}.generate()
//
//// -----------------------
//
//// bo_1px_solid_gray/w_400,q_auto,dpr_2,f_auto
//cloudinary.url {
//publicId("sample")
//border(Border.solid() {
//width(1)
//color(Color.GRAY)
//})
//resize(Resize.scale(400))
//format(Format.auto())
//quality(Quality.auto())
//dpr(2)
//}.generate()
//
//// -----------------------
//
//// bo_1px_solid_gray/w_450,f_auto,dpr_2,q_auto
//cloudinary.url {
//publicId("sample")
//border(Border.solid() {
//width(1)
//color(Color.GRAY)
//})
//resize(Resize.scale(450))
//format(Format.auto())
//quality(Quality.auto())
//dpr(2)
//}.generate()
//
//// -----------------------
//
//// bo_1px_solid_gray/w_500,f_auto,q_auto,dpr_2
//cloudinary.url {
//publicId("sample")
//border(Border.solid() {
//width(1)
//color(Color.GRAY)
//})
//resize(Resize.scale(500))
//format(Format.auto())
//quality(Quality.auto())
//dpr(2)
//}.generate()
//
//// -----------------------
//
//// bo_1px_solid_gray/w_550,q_auto,f_auto,dpr_2
//cloudinary.url {
//publicId("sample")
//border(Border.solid() {
//width(1)
//color(Color.GRAY)
//})
//resize(Resize.scale(550))
//format(Format.auto())
//quality(Quality.auto())
//dpr(2)
//}.generate()
//
//// -----------------------
//
//// bo_1px_solid_gray/w_600,f_auto,q_auto,dpr_2
//cloudinary.url {
//publicId("sample")
//border(Border.solid() {
//width(1)
//color(Color.GRAY)
//})
//resize(Resize.scale(600))
//format(Format.auto())
//quality(Quality.auto())
//dpr(2)
//}.generate()
//
//// -----------------------
//
//// bo_1px_solid_rgb:ccc,f_auto,dpr_2
//cloudinary.url {
//publicId("sample")
//border(Border.solid() {
//width(1)
//color(Color.rgb("#ccc"))
//})
//format(Format.auto())
//dpr(2)
//}.generate()
//
//// -----------------------
//
//// bo_2px_solid_black
//cloudinary.url {
//publicId("sample")
//border(Border.solid() {
//width(2)
//color(Color.BLACK)
//})
//}.generate()
//
//// -----------------------
//
//// bo_2px_solid_black,f_auto,q_auto
//cloudinary.url {
//publicId("sample")
//border(Border.solid() {
//width(2)
//color(Color.BLACK)
//})
//format(Format.auto())
//quality(Quality.auto())
//}.generate()
//
//// -----------------------
//
//// bo_2px_solid_black,so_256
//cloudinary.url {
//publicId("sample")
//trim(Trim.range(256))
//border(Border.solid() {
//width(2)
//color(Color.BLACK)
//})
//}.generate()
//
//// -----------------------
//
//// bo_2px_solid_black,so_98
//cloudinary.url {
//publicId("sample")
//trim(Trim.range(98))
//border(Border.solid() {
//width(2)
//color(Color.BLACK)
//})
//}.generate()
//
//// -----------------------
//
//// bo_5px_solid_black,f_auto,q_auto/dpr_auto
//cloudinary.url {
//publicId("sample")
//border(Border.solid() {
//width(5)
//color(Color.BLACK)
//})
//quality(Quality.auto())
//dpr(Dpr.auto())
//}.generate()
//
//// -----------------------
//
//// bo_5px_solid_red
//cloudinary.url {
//publicId("sample")
//border(Border.solid() {
//width(5)
//color(Color.RED)
//})
//}.generate()
//
//// -----------------------
//
//// br_250k
//cloudinary.url {
//publicId("sample")
//encode(Encode.bitRate("250k"))
//}.generate()
//
//// -----------------------
//
//// c_crop,ar_1.85356,so_813,g_south
//cloudinary.url {
//publicId("sample")
//trim(Trim.range(813))
//resize(Resize.crop() {
//aspectRatio(1.85356)
//gravity(Gravity.south())
//})
//}.generate()
//
//// -----------------------
//
//// c_crop,e_sepia,h_150,r_20,w_150
//cloudinary.url {
//publicId("sample")
//roundCorners(20)
//effect(Effect.sepia())
//resize(Resize.crop(150, 150))
//}.generate()
//
//// -----------------------
//
//// c_crop,e_viesus_correct,g_south,r_20,w_1000/c_scale,g_north_east,l_viesus_logo,o_50,w_100,x_10,y_10/c_scale,w_600
//cloudinary.url {
//publicId("sample")
//roundCorners(20)
//adjust(Adjust.viesusCorrect())
//resize(Resize.crop(1000) {
//gravity(Gravity.south())
//})
//overlay(Layer.image("viesus_logo") {
//adjust(Adjust.opacity(50))
//resize(Resize.scale(100) {
//gravity(Gravity.northEast(10, 10))
//})
//})
//resize(Resize.scale(600))
//}.generate()
//
//// -----------------------
//
//// c_crop,g_cat:dog
//cloudinary.url {
//publicId("sample")
//resize(Resize.crop() {
//gravity(Gravity.object(ObjectGravity.CAT, ObjectGravity.DOG))
//})
//}.generate()
//
//// -----------------------
//
//// c_crop,g_cat:dog/w_0.25,c_scale
//cloudinary.url {
//publicId("sample")
//resize(Resize.crop() {
//gravity(Gravity.object(ObjectGravity.CAT, ObjectGravity.DOG))
//})
//resize(Resize.scale(0.25))
//}.generate()
//
//// -----------------------
//
//// c_crop,g_dog:auto:bird,w_600,h_800
//cloudinary.url {
//publicId("sample")
//resize(Resize.crop(600, 800) {
//gravity(Gravity.object(ObjectGravity.DOG, ObjectGravity.AUTO, ObjectGravity.BIRD))
//})
//}.generate()
//
//// -----------------------
//
//// c_crop,g_dog:auto:bird,w_600,h_800/w_0.25,c_scale
//cloudinary.url {
//publicId("sample")
//resize(Resize.crop(600, 800) {
//gravity(Gravity.object(ObjectGravity.DOG, ObjectGravity.AUTO, ObjectGravity.BIRD))
//})
//resize(Resize.scale(0.25))
//}.generate()
//
//// -----------------------
//
//// c_crop,g_dog:bird,w_600,h_800
//cloudinary.url {
//publicId("sample")
//resize(Resize.crop(600, 800) {
//gravity(Gravity.object(ObjectGravity.DOG, ObjectGravity.BIRD))
//})
//}.generate()
//
//// -----------------------
//
//// c_crop,g_dog:bird,w_600,h_800/w_0.25,c_scale
//cloudinary.url {
//publicId("sample")
//resize(Resize.crop(600, 800) {
//gravity(Gravity.object(ObjectGravity.DOG, ObjectGravity.BIRD))
//})
//resize(Resize.scale(0.25))
//}.generate()
//
//// -----------------------
//
//// c_crop,g_dog:cat
//cloudinary.url {
//publicId("sample")
//resize(Resize.crop() {
//gravity(Gravity.object(ObjectGravity.DOG, ObjectGravity.CAT))
//})
//}.generate()
//
//// -----------------------
//
//// c_crop,g_dog:cat/w_0.25,c_scale
//cloudinary.url {
//publicId("sample")
//resize(Resize.crop() {
//gravity(Gravity.object(ObjectGravity.DOG, ObjectGravity.CAT))
//})
//resize(Resize.scale(0.25))
//}.generate()
//
//// -----------------------
//
//// c_crop,g_face
//cloudinary.url {
//publicId("sample")
//resize(Resize.crop() {
//gravity(Gravity.face())
//})
//}.generate()
//
//// -----------------------
//
//// c_crop,g_face,h_400,w_400
//cloudinary.url {
//publicId("sample")
//resize(Resize.crop(400, 400) {
//gravity(Gravity.face())
//})
//}.generate()
//
//// -----------------------
//
//// c_crop,g_face,r_75,bo_10px_solid_grey/l_young_couple,w_150,h_150,c_thumb,r_max,g_faces,bo_3px_solid_black/fl_layer_apply,g_north_east
//cloudinary.url {
//publicId("sample")
//roundCorners(75)
//border(Border.solid() {
//width(10)
//color(Color.GREY)
//})
//resize(Resize.crop() {
//gravity(Gravity.face())
//})
//overlay(Layer.image("young_couple") {
//roundCorners(RoundCorners.max())
//border(Border.solid() {
//width(3)
//color(Color.BLACK)
//})
//resize(Resize.thumbnail(150, 150) {
//gravity(Gravity.faces())
//})
//}, Position.northEast())
//}.generate()
//
//// -----------------------
//
//// c_crop,g_microwave
//cloudinary.url {
//publicId("sample")
//resize(Resize.crop() {
//gravity(Gravity.microwave())
//})
//}.generate()
//
//// -----------------------
//
//// c_crop,g_microwave/w_0.3,c_scale
//cloudinary.url {
//publicId("sample")
//resize(Resize.crop() {
//gravity(Gravity.microwave())
//})
//resize(Resize.scale(0.3))
//}.generate()
//
//// -----------------------
//
//// c_crop,g_north,h_200,w_260
//cloudinary.url {
//publicId("sample")
//resize(Resize.crop(260, 200) {
//gravity(Gravity.north())
//})
//}.generate()
//
//// -----------------------
//
//// c_crop,g_refrigerator
//cloudinary.url {
//publicId("sample")
//resize(Resize.crop() {
//gravity(Gravity.refrigerator())
//})
//}.generate()
//
//// -----------------------
//
//// c_crop,g_refrigerator/w_0.3,c_scale
//cloudinary.url {
//publicId("sample")
//resize(Resize.crop() {
//gravity(Gravity.refrigerator())
//})
//resize(Resize.scale(0.3))
//}.generate()
//
//// -----------------------
//
//// c_crop,g_sink
//cloudinary.url {
//publicId("sample")
//resize(Resize.crop() {
//gravity(Gravity.sink())
//})
//}.generate()
//
//// -----------------------
//
//// c_crop,g_sink/w_0.3,c_scale
//cloudinary.url {
//publicId("sample")
//resize(Resize.crop() {
//gravity(Gravity.sink())
//})
//resize(Resize.scale(0.3))
//}.generate()
//
//// -----------------------
//
//// c_crop,h_100,w_150,x_380,y_250/c_fill,h_100,w_130/a_20/c_scale,w_0.8
//cloudinary.url {
//publicId("sample")
//resize(Resize.crop(150, 100) {
//gravity(Gravity.absolute(380, 250))
//})
//resize(Resize.fill(130, 100))
//rotate(Rotate.angle(20))
//resize(Resize.scale(0.8))
//}.generate()
//
//// -----------------------
//
//// c_crop,w_300,h_300/c_scale,w_150,r_max
//cloudinary.url {
//publicId("sample")
//resize(Resize.crop(300, 300))
//roundCorners(RoundCorners.max())
//resize(Resize.scale(150))
//}.generate()
//
//// -----------------------
//
//// c_fill,ar_0.7,w_200
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(200) {
//aspectRatio(0.7)
//})
//}.generate()
//
//// -----------------------
//
//// c_fill,ar_0.7,w_750
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(750) {
//aspectRatio(0.7)
//})
//}.generate()
//
//// -----------------------
//
//// c_fill,e_sepia,h_150,r_20,w_150/g_north,l_text:arial_60:This is my picture,y_20/a_20
//cloudinary.url {
//publicId("sample")
//roundCorners(20)
//effect(Effect.sepia())
//resize(Resize.fill(150, 150))
//overlay(Layer.text("This is my picture") {
//fontFamily("arial")
//fontSize(60)
//}, Position.north() {
//y(20)
//})
//rotate(Rotate.angle(20))
//}.generate()
//
//// -----------------------
//
//// c_fill,e_sepia,h_150,r_20,w_150/g_north,l_text:arial_60:Thishttps
//cloudinary.url {
//publicId("sample")
//roundCorners(20)
//effect(Effect.sepia())
//resize(Resize.fill(150, 150))
//overlay(Layer.text("Thishttps") {
//fontFamily("arial")
//fontSize(60)
//}, Position.north())
//}.generate()
//
//// -----------------------
//
//// c_fill,e_style_transfer,h_700,l_lighthouse,w_700
//cloudinary.url {
//publicId("sample")
//effect(Effect.styleTransfer() {
//overlay(Layer.image("lighthouse") {
//resize(Resize.fill(700, 700))
//})
//})
//}.generate()
//
//// -----------------------
//
//// c_fill,e_style_transfer,h_700,l_lighthouse,w_700/c_scale,w_250
//cloudinary.url {
//publicId("sample")
//effect(Effect.styleTransfer() {
//overlay(Layer.image("lighthouse") {
//resize(Resize.fill(700, 700))
//})
//})
//resize(Resize.scale(250))
//}.generate()
//
//// -----------------------
//
//// c_fill,g_auto,h_250,w_250
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(250, 250) {
//gravity(Gravity.auto())
//})
//}.generate()
//
//// -----------------------
//
//// c_fill,g_auto,h_400,ar_0.8
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill() {
//height(400)
//aspectRatio(0.8)
//gravity(Gravity.auto())
//})
//}.generate()
//
//// -----------------------
//
//// c_fill,g_auto,h_400,ar_0.8/h_300
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill() {
//height(400)
//aspectRatio(0.8)
//gravity(Gravity.auto())
//})
//resize(Resize.scale() {
//height(300)
//})
//}.generate()
//
//// -----------------------
//
//// c_fill,g_auto,h_500,w_500
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(500, 500) {
//gravity(Gravity.auto())
//})
//}.generate()
//
//// -----------------------
//
//// c_fill,g_auto:classic,ar_0.7,w_200
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(200) {
//aspectRatio(0.7)
//gravity(Gravity.auto(ObjectGravity.CLASSIC))
//})
//}.generate()
//
//// -----------------------
//
//// c_fill,g_auto:classic,ar_0.7,w_750
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(750) {
//aspectRatio(0.7)
//gravity(Gravity.auto(ObjectGravity.CLASSIC))
//})
//}.generate()
//
//// -----------------------
//
//// c_fill,g_auto:ocr_text_avoid,h_400,ar_0.8
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill() {
//height(400)
//aspectRatio(0.8)
//gravity(Gravity.auto(ObjectGravity.OCR_TEXT_AVOID))
//})
//}.generate()
//
//// -----------------------
//
//// c_fill,g_auto:ocr_text_avoid,h_400,ar_0.8/h_300
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill() {
//height(400)
//aspectRatio(0.8)
//gravity(Gravity.auto(ObjectGravity.OCR_TEXT_AVOID))
//})
//resize(Resize.scale() {
//height(300)
//})
//}.generate()
//
//// -----------------------
//
//// c_fill,g_auto:subject,ar_0.7,w_200
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(200) {
//aspectRatio(0.7)
//gravity(Gravity.auto(ObjectGravity.SUBJECT))
//})
//}.generate()
//
//// -----------------------
//
//// c_fill,g_north,h_200,w_150
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(150, 200) {
//gravity(Gravity.north())
//})
//}.generate()
//
//// -----------------------
//
//// c_fill,g_ocr_text,h_250,w_250
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(250, 250) {
//gravity(Gravity.ocrText())
//})
//}.generate()
//
//// -----------------------
//
//// c_fill,g_ocr_text,h_500,w_500
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(500, 500) {
//gravity(Gravity.ocrText())
//})
//}.generate()
//
//// -----------------------
//
//// c_fill,h_150,q_jpegmini,w_200
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(200, 150))
//quality(Quality.jpegMini())
//}.generate()
//
//// -----------------------
//
//// c_fill,h_150,w_100
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(100, 150))
//}.generate()
//
//// -----------------------
//
//// c_fill,h_200,w_200/e_brightness:100,h_200,u_site_bg,w_200
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(200, 200))
//underlay(Layer.image("site_bg") {
//adjust(Adjust.brightness(100))
//resize(Resize.scale(200, 200))
//})
//}.generate()
//
//// -----------------------
//
//// c_fill,h_200,w_300,r_20
//cloudinary.url {
//publicId("sample")
//roundCorners(20)
//resize(Resize.fill(300, 200))
//}.generate()
//
//// -----------------------
//
//// c_fill,h_250,w_250
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(250, 250))
//}.generate()
//
//// -----------------------
//
//// c_fill,h_300,w_300
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(300, 300))
//}.generate()
//
//// -----------------------
//
//// c_fill,h_500,w_500
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(500, 500))
//}.generate()
//
//// -----------------------
//
//// c_fill,h_700,w_700/e_style_transfer:60,l_lighthouse
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(700, 700))
//effect(Effect.styleTransfer() {
//overlay(Layer.image("lighthouse"))
//strength(60)
//})
//}.generate()
//
//// -----------------------
//
//// c_fill,h_700,w_700/e_style_transfer:preserve_color,l_lighthouse
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(700, 700))
//effect(Effect.styleTransfer() {
//overlay(Layer.image("lighthouse"))
//strength("preserve_color")
//})
//}.generate()
//
//// -----------------------
//
//// c_fill,h_700,w_700/e_style_transfer:preserve_color:40,l_lighthouse
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(700, 700))
//effect(Effect.styleTransfer() {
//overlay(Layer.image("lighthouse"))
//strength(40)
//preserveColor(true)
//})
//}.generate()
//
//// -----------------------
//
//// c_fill,h_700,w_700/e_style_transfer:preserve_color:40,l_lighthouse/w_250
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(700, 700))
//effect(Effect.styleTransfer() {
//overlay(Layer.image("lighthouse"))
//strength(40)
//preserveColor(true)
//})
//resize(Resize.scale(250))
//}.generate()
//
//// -----------------------
//
//// c_fill,r_50,w_400,h_400,g_north,f_auto,bo_2px_solid_rgb:999/e_shadow:20,co_rgb:ccc/a_30,co_rgb:ec9800,e_colorize,l_url2png_logo
//cloudinary.url {
//publicId("sample")
//roundCorners(50)
//border(Border.solid() {
//width(2)
//color(Color.rgb("#999"))
//})
//resize(Resize.fill(400, 400) {
//gravity(Gravity.north())
//})
//effect(Effect.shadow(20) {
//color(Color.rgb("#ccc"))
//})
//overlay(Layer.image("url2png_logo") {
//effect(Effect.colorize() {
//color(Color.rgb("#ec9800"))
//})
//rotate(Rotate.angle(30))
//})
//}.generate()
//
//// -----------------------
//
//// c_fill,w_100,h_100
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(100, 100))
//}.generate()
//
//// -----------------------
//
//// c_fit,h_16,w_16/e_sepia
//cloudinary.url {
//publicId("sample")
//resize(Resize.fit(16, 16))
//effect(Effect.sepia())
//}.generate()
//
//// -----------------------
//
//// c_fit,w_10/a_15
//cloudinary.url {
//publicId("sample")
//resize(Resize.fit(10))
//rotate(Rotate.angle(15))
//}.generate()
//
//// -----------------------
//
//// c_imagga_crop
//cloudinary.url {
//publicId("sample")
//resize(Resize.imaggaCrop())
//}.generate()
//
//// -----------------------
//
//// c_imagga_crop,e_saturation:50,h_300,r_20,w_450/g_south_west,l_imagga_logo,o_80,w_100,x_10,y_10
//cloudinary.url {
//publicId("sample")
//roundCorners(20)
//adjust(Adjust.saturation(50))
//resize(Resize.imaggaCrop(450, 300))
//overlay(Layer.image("imagga_logo") {
//adjust(Adjust.opacity(80))
//resize(Resize.scale(100) {
//gravity(Gravity.southWest(10, 10))
//})
//})
//}.generate()
//
//// -----------------------
//
//// c_imagga_crop,h_380,w_380
//cloudinary.url {
//publicId("sample")
//resize(Resize.imaggaCrop(380, 380))
//}.generate()
//
//// -----------------------
//
//// c_imagga_scale,h_200,w_150
//cloudinary.url {
//publicId("sample")
//resize(Resize.imaggaScale(150, 200))
//}.generate()
//
//// -----------------------
//
//// c_imagga_scale,h_240,w_260
//cloudinary.url {
//publicId("sample")
//resize(Resize.imaggaScale(260, 240))
//}.generate()
//
//// -----------------------
//
//// c_imagga_scale,w_150,h_200
//cloudinary.url {
//publicId("sample")
//resize(Resize.imaggaScale(150, 200))
//}.generate()
//
//// -----------------------
//
//// c_lpad,w_480,h_320,vs_2,q_70,br_1400k,vc_h264:baseline:3.0
//cloudinary.url {
//publicId("sample")
//transcode(VideoCodec.h264(VideoCodecProfile.VCP_BASELINE, VideoCodecLevel.VCL_30))
//resize(Resize.limitPad(480, 320))
//encode(Encode.bitRate("1400k"))
//videoSampling(2)
//quality(70)
//}.generate()
//
//// -----------------------
//
//// c_lpad,w_960,h_640,vs_2,q_70,br_5m,vc_h264:main:3.1
//cloudinary.url {
//publicId("sample")
//transcode(VideoCodec.h264(VideoCodecProfile.VCP_MAIN, VideoCodecLevel.VCL_31))
//resize(Resize.limitPad(960, 640))
//encode(Encode.bitRate("5m"))
//videoSampling(2)
//quality(70)
//}.generate()
//
//// -----------------------
//
//// c_pad,du_10,h_360,q_70,w_480
//cloudinary.url {
//publicId("sample")
//trim(Trim.duration(10))
//resize(Resize.pad(480, 360))
//quality(70)
//}.generate()
//
//// -----------------------
//
//// c_pad,h_300,w_400
//cloudinary.url {
//publicId("sample")
//resize(Resize.pad(400, 300))
//}.generate()
//
//// -----------------------
//
//// c_pad,h_320,w_480,b_blurred:400:15
//cloudinary.url {
//publicId("sample")
//resize(Resize.pad(480, 320) {
//background(Color.BLURRED {
//intensity(400)
//brightness(15)
//})
//})
//}.generate()
//
//// -----------------------
//
//// c_pad,h_360,w_480,q_70,du_10
//cloudinary.url {
//publicId("sample")
//trim(Trim.duration(10))
//resize(Resize.pad(480, 360))
//quality(70)
//}.generate()
//
//// -----------------------
//
//// c_pad,w_200,h_200,e_saturation:50/e_shadow/bo_1px_solid_rgb:666
//cloudinary.url {
//publicId("sample")
//adjust(Adjust.saturation(50))
//resize(Resize.pad(200, 200))
//effect(Effect.shadow())
//border(Border.solid() {
//width(1)
//color(Color.rgb("#666"))
//})
//}.generate()
//
//// -----------------------
//
//// c_pad,w_400
//cloudinary.url {
//publicId("sample")
//resize(Resize.pad(400))
//}.generate()
//
//// -----------------------
//
//// c_scale,dl_1000,e_trim,w_700
//cloudinary.url {
//publicId("sample")
//effect(Effect.trim())
//resize(Resize.scale(700))
//delay(1000)
//}.generate()
//
//// -----------------------
//
//// c_scale,e_grayscale,f_auto,q_auto,w_100
//cloudinary.url {
//publicId("sample")
//effect(Effect.grayscale())
//resize(Resize.scale(100))
//format(Format.auto())
//quality(Quality.auto())
//}.generate()
//
//// -----------------------
//
//// c_scale,e_saturation:50,r_20,w_400/g_south_east,l_logo_jpegmini,o_80,w_100,x_10,y_10/q_jpegmini
//cloudinary.url {
//publicId("sample")
//roundCorners(20)
//adjust(Adjust.saturation(50))
//resize(Resize.scale(400))
//overlay(Layer.image("logo_jpegmini") {
//adjust(Adjust.opacity(80))
//resize(Resize.scale(100) {
//gravity(Gravity.southEast(10, 10))
//})
//})
//quality(Quality.jpegMini())
//}.generate()
//
//// -----------------------
//
//// c_scale,e_viesus_correct,w_400
//cloudinary.url {
//publicId("sample")
//adjust(Adjust.viesusCorrect())
//resize(Resize.scale(400))
//}.generate()
//
//// -----------------------
//
//// c_scale,e_viesus_correct,w_500
//cloudinary.url {
//publicId("sample")
//adjust(Adjust.viesusCorrect())
//resize(Resize.scale(500))
//}.generate()
//
//// -----------------------
//
//// c_scale,q_jpegmini,w_400
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(400))
//quality(Quality.jpegMini())
//}.generate()
//
//// -----------------------
//
//// c_scale,q_jpegmini:1,w_400
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(400))
//quality(Quality.jpegMini(JpegMini.HIGH))
//}.generate()
//
//// -----------------------
//
//// c_scale,w_400
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(400))
//}.generate()
//
//// -----------------------
//
//// c_scale,w_400/c_imagga_scale,h_150,l_family_bench.png,r_max,w_150
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(400))
//overlay(Layer.image("family_bench.png") {
//roundCorners(RoundCorners.max())
//resize(Resize.imaggaScale(150, 150))
//})
//}.generate()
//
//// -----------------------
//
//// c_thumb,g_adv_face,h_100,w_100
//cloudinary.url {
//publicId("sample")
//resize(Resize.thumbnail(100, 100) {
//gravity(Gravity.advFace())
//})
//}.generate()
//
//// -----------------------
//
//// c_thumb,g_adv_face,w_100,h_100
//cloudinary.url {
//publicId("sample")
//resize(Resize.thumbnail(100, 100) {
//gravity(Gravity.advFace())
//})
//}.generate()
//
//// -----------------------
//
//// c_thumb,g_adv_faces,w_150,h_150
//cloudinary.url {
//publicId("sample")
//resize(Resize.thumbnail(150, 150) {
//gravity(Gravity.advFaces())
//})
//}.generate()
//
//// -----------------------
//
//// c_thumb,w_100,h_100,g_face,r_max/l_cloudinary_icon,e_brightness:200,fl_relative,w_0.5,o_60/dpr_1.0
//cloudinary.url {
//publicId("sample")
//roundCorners(RoundCorners.max())
//resize(Resize.thumbnail(100, 100) {
//gravity(Gravity.face())
//})
//overlay(Layer.image("cloudinary_icon") {
//adjust(Adjust.brightness(200))
//adjust(Adjust.opacity(60))
//resize(Resize.scale(0.5))
//addFlag(Flag.relative())
//})
//dpr(1.0)
//}.generate()
//
//// -----------------------
//
//// c_thumb,w_100,h_100,g_face,r_max/l_cloudinary_icon,e_brightness:200,fl_relative,w_0.5,o_60/dpr_2.0
//cloudinary.url {
//publicId("sample")
//roundCorners(RoundCorners.max())
//resize(Resize.thumbnail(100, 100) {
//gravity(Gravity.face())
//})
//overlay(Layer.image("cloudinary_icon") {
//adjust(Adjust.brightness(200))
//adjust(Adjust.opacity(60))
//resize(Resize.scale(0.5))
//addFlag(Flag.relative())
//})
//dpr(2.0)
//}.generate()
//
//// -----------------------
//
//// c_thumb,w_100,h_100,g_face,r_max/l_cloudinary_icon,e_brightness:200,fl_relative,w_0.5,o_60/dpr_3.0
//cloudinary.url {
//publicId("sample")
//roundCorners(RoundCorners.max())
//resize(Resize.thumbnail(100, 100) {
//gravity(Gravity.face())
//})
//overlay(Layer.image("cloudinary_icon") {
//adjust(Adjust.brightness(200))
//adjust(Adjust.opacity(60))
//resize(Resize.scale(0.5))
//addFlag(Flag.relative())
//})
//dpr(3.0)
//}.generate()
//
//// -----------------------
//
//// c_thumb,w_100,h_100,g_face,r_max/l_cloudinary_icon,e_brightness:200,w_50,o_60/dpr_2.0
//cloudinary.url {
//publicId("sample")
//roundCorners(RoundCorners.max())
//resize(Resize.thumbnail(100, 100) {
//gravity(Gravity.face())
//})
//overlay(Layer.image("cloudinary_icon") {
//adjust(Adjust.brightness(200))
//adjust(Adjust.opacity(60))
//resize(Resize.scale(50))
//})
//dpr(2.0)
//}.generate()
//
//// -----------------------
//
//// co_rgb:009900,e_shadow:50,x_10,y_10
//cloudinary.url {
//publicId("sample")
//effect(Effect.shadow(50) {
//color(Color.rgb("#009900"))
//})
//gravity(Gravity.absolute(10, 10))
//}.generate()
//
//// -----------------------
//
//// dl_100
//cloudinary.url {
//publicId("sample")
//delay(100)
//}.generate()
//
//// -----------------------
//
//// dl_200
//cloudinary.url {
//publicId("sample")
//delay(200)
//}.generate()
//
//// -----------------------
//
//// dn_20
//cloudinary.url {
//publicId("sample")
//transcode(Transcode.density(20))
//}.generate()
//
//// -----------------------
//
//// dpr_2,f_auto,q_auto,bo_1px_solid_black
//cloudinary.url {
//publicId("sample")
//border(Border.solid() {
//width(1)
//color(Color.BLACK)
//})
//format(Format.auto())
//quality(Quality.auto())
//dpr(2)
//}.generate()
//
//// -----------------------
//
//// dpr_2,f_auto,q_auto,w_800
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(800))
//format(Format.auto())
//quality(Quality.auto())
//dpr(2)
//}.generate()
//
//// -----------------------
//
//// dpr_2,f_auto,q_auto/bo_1px_solid_gray
//cloudinary.url {
//publicId("sample")
//quality(Quality.auto())
//dpr(2)
//border(Border.solid() {
//width(1)
//color(Color.GRAY)
//})
//}.generate()
//
//// -----------------------
//
//// dpr_2,q_auto,f_auto
//cloudinary.url {
//publicId("sample")
//format(Format.auto())
//quality(Quality.auto())
//dpr(2)
//}.generate()
//
//// -----------------------
//
//// dpr_2,q_auto,f_auto,w_500,bo_1px_solid_gray
//cloudinary.url {
//publicId("sample")
//border(Border.solid() {
//width(1)
//color(Color.GRAY)
//})
//resize(Resize.scale(500))
//format(Format.auto())
//quality(Quality.auto())
//dpr(2)
//}.generate()
//
//// -----------------------
//
//// dpr_2,q_auto,f_auto,w_600
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(600))
//format(Format.auto())
//quality(Quality.auto())
//dpr(2)
//}.generate()
//
//// -----------------------
//
//// dpr_2,q_auto,f_auto/bo_1px_solid_gray
//cloudinary.url {
//publicId("sample")
//quality(Quality.auto())
//dpr(2)
//border(Border.solid() {
//width(1)
//color(Color.GRAY)
//})
//}.generate()
//
//// -----------------------
//
//// dpr_auto,w_600/bo_1px_solid_gray
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(600))
//dpr(Dpr.auto())
//border(Border.solid() {
//width(1)
//color(Color.GRAY)
//})
//}.generate()
//
//// -----------------------
//
//// dpr_auto/bo_1px_solid_gray
//cloudinary.url {
//publicId("sample")
//dpr(Dpr.auto())
//border(Border.solid() {
//width(1)
//color(Color.GRAY)
//})
//}.generate()
//
//// -----------------------
//
//// dpr_auto/bo_1px_solid_gray/h_300
//cloudinary.url {
//publicId("sample")
//dpr(Dpr.auto())
//border(Border.solid() {
//width(1)
//color(Color.GRAY)
//})
//resize(Resize.scale() {
//height(300)
//})
//}.generate()
//
//// -----------------------
//
//// dpr_auto/h_300/bo_1px_solid_gray
//cloudinary.url {
//publicId("sample")
//dpr(Dpr.auto())
//resize(Resize.scale() {
//height(300)
//})
//border(Border.solid() {
//width(1)
//color(Color.GRAY)
//})
//}.generate()
//
//// -----------------------
//
//// du_30,q_70:qmax_20
//cloudinary.url {
//publicId("sample")
//trim(Trim.duration(30))
//quality(Quality.level(70) {
//quantization(20)
//})
//}.generate()
//
//// -----------------------
//
//// du_5,w_300,h_200/l_video:dog,w_300,h_200/du_5/l_video:transition1,e_transition/e_accelerate:5s,a_180/fl_layer_apply/fl_layer_apply
//cloudinary.url {
//publicId("sample")
//trim(Trim.duration(5))
//resize(Resize.scale(300, 200))
//overlay(Layer.video("dog") {
//resize(Resize.scale(300, 200))
//trim(Trim.duration(5))
//overlay(Layer.video("transition1") {
//effect(Effect.transition())
//effect(Effect.accelerate(Accelerate.5s()))
//rotate(Rotate.angle(180))
//})
//})
//}.generate()
//
//// -----------------------
//
//// e_adv_redeye/w_300,h_80,c_thumb,g_adv_eyes
//cloudinary.url {
//publicId("sample")
//effect(Effect.advRedeye())
//position(Position.advancedEyes())
//resize(Resize.thumbnail(300, 80))
//}.generate()
//
//// -----------------------
//
//// e_art:al_dente
//cloudinary.url {
//publicId("sample")
//effect(Effect.artisticFilter(ArtisticFilter.AL_DENTE))
//}.generate()
//
//// -----------------------
//
//// e_art:athena
//cloudinary.url {
//publicId("sample")
//effect(Effect.artisticFilter(ArtisticFilter.ATHENA))
//}.generate()
//
//// -----------------------
//
//// e_art:audrey
//cloudinary.url {
//publicId("sample")
//effect(Effect.artisticFilter(ArtisticFilter.AUDREY))
//}.generate()
//
//// -----------------------
//
//// e_art:aurora
//cloudinary.url {
//publicId("sample")
//effect(Effect.artisticFilter(ArtisticFilter.AURORA))
//}.generate()
//
//// -----------------------
//
//// e_art:daguerre
//cloudinary.url {
//publicId("sample")
//effect(Effect.artisticFilter(ArtisticFilter.DAGUERRE))
//}.generate()
//
//// -----------------------
//
//// e_art:eucalyptus
//cloudinary.url {
//publicId("sample")
//effect(Effect.artisticFilter(ArtisticFilter.EUCALYPTUS))
//}.generate()
//
//// -----------------------
//
//// e_art:fes
//cloudinary.url {
//publicId("sample")
//effect(Effect.artisticFilter(ArtisticFilter.FES))
//}.generate()
//
//// -----------------------
//
//// e_art:frost
//cloudinary.url {
//publicId("sample")
//effect(Effect.artisticFilter(ArtisticFilter.FROST))
//}.generate()
//
//// -----------------------
//
//// e_art:hairspray
//cloudinary.url {
//publicId("sample")
//effect(Effect.artisticFilter(ArtisticFilter.HAIRSPRAY))
//}.generate()
//
//// -----------------------
//
//// e_art:hokusai
//cloudinary.url {
//publicId("sample")
//effect(Effect.artisticFilter(ArtisticFilter.HOKUSAI))
//}.generate()
//
//// -----------------------
//
//// e_art:incognito
//cloudinary.url {
//publicId("sample")
//effect(Effect.artisticFilter(ArtisticFilter.INCOGNITO))
//}.generate()
//
//// -----------------------
//
//// e_art:linen
//cloudinary.url {
//publicId("sample")
//effect(Effect.artisticFilter(ArtisticFilter.LINEN))
//}.generate()
//
//// -----------------------
//
//// e_art:peacock
//cloudinary.url {
//publicId("sample")
//effect(Effect.artisticFilter(ArtisticFilter.PEACOCK))
//}.generate()
//
//// -----------------------
//
//// e_art:primavera
//cloudinary.url {
//publicId("sample")
//effect(Effect.artisticFilter(ArtisticFilter.PRIMAVERA))
//}.generate()
//
//// -----------------------
//
//// e_art:quartz
//cloudinary.url {
//publicId("sample")
//effect(Effect.artisticFilter(ArtisticFilter.QUARTZ))
//}.generate()
//
//// -----------------------
//
//// e_art:red_rock
//cloudinary.url {
//publicId("sample")
//effect(Effect.artisticFilter(ArtisticFilter.RED_ROCK))
//}.generate()
//
//// -----------------------
//
//// e_art:refresh
//cloudinary.url {
//publicId("sample")
//effect(Effect.artisticFilter(ArtisticFilter.REFRESH))
//}.generate()
//
//// -----------------------
//
//// e_art:sizzle
//cloudinary.url {
//publicId("sample")
//effect(Effect.artisticFilter(ArtisticFilter.SIZZLE))
//}.generate()
//
//// -----------------------
//
//// e_art:sonnet
//cloudinary.url {
//publicId("sample")
//effect(Effect.artisticFilter(ArtisticFilter.SONNET))
//}.generate()
//
//// -----------------------
//
//// e_art:ukulele
//cloudinary.url {
//publicId("sample")
//effect(Effect.artisticFilter(ArtisticFilter.UKULELE))
//}.generate()
//
//// -----------------------
//
//// e_art:zorro
//cloudinary.url {
//publicId("sample")
//effect(Effect.artisticFilter(ArtisticFilter.ZORRO))
//}.generate()
//
//// -----------------------
//
//// e_assist_colorblind:8
//cloudinary.url {
//publicId("sample")
//effect(Effect.assistColorblind(8))
//}.generate()
//
//// -----------------------
//
//// e_assist_colorblind:8/e_simulate_colorblind
//cloudinary.url {
//publicId("sample")
//effect(Effect.assistColorblind(8))
//effect(Effect.simulateColorblind())
//}.generate()
//
//// -----------------------
//
//// e_assist_colorblind:xray
//cloudinary.url {
//publicId("sample")
//effect(Effect.assistColorblind(AssistColorblind.xray()))
//}.generate()
//
//// -----------------------
//
//// e_blur:300
//cloudinary.url {
//publicId("sample")
//effect(Effect.blur(300))
//}.generate()
//
//// -----------------------
//
//// e_blur:500/l_video:dog,w_600/w_500
//cloudinary.url {
//publicId("sample")
//effect(Effect.blur(500))
//overlay(Layer.video("dog") {
//resize(Resize.scale(600))
//})
//resize(Resize.scale(500))
//}.generate()
//
//// -----------------------
//
//// e_blur_region:800,g_ocr_text
//cloudinary.url {
//publicId("sample")
//effect(Effect.blurRegion() {
//level(800)
//gravity(Gravity.ocrText())
//})
//}.generate()
//
//// -----------------------
//
//// e_blur_region:800,g_ocr_text/h_300
//cloudinary.url {
//publicId("sample")
//effect(Effect.blurRegion() {
//level(800)
//gravity(Gravity.ocrText())
//})
//resize(Resize.scale() {
//height(300)
//})
//}.generate()
//
//// -----------------------
//
//// e_cartoonify/r_max/e_outline:100,co_lightblue/b_lightblue/h_300
//cloudinary.url {
//publicId("sample")
//effect(Effect.cartoonify())
//roundCorners(RoundCorners.max())
//effect(Effect.outline(100) {
//color(Color.LIGHTBLUE)
//})
//background(Color.LIGHTBLUE)
//resize(Resize.scale() {
//height(300)
//})
//}.generate()
//
//// -----------------------
//
//// e_deshake:32
//cloudinary.url {
//publicId("sample")
//effect(Effect.deshake(32))
//}.generate()
//
//// -----------------------
//
//// e_fade:2000/e_fade:-4000
//cloudinary.url {
//publicId("sample")
//effect(Effect.fadeIn(2000))
//effect(Effect.fadeOut(4000))
//}.generate()
//
//// -----------------------
//
//// e_grayscale
//cloudinary.url {
//publicId("sample")
//effect(Effect.grayscale())
//}.generate()
//
//// -----------------------
//
//// e_improve:outdoor
//cloudinary.url {
//publicId("sample")
//adjust(Adjust.improve(Improve.OUTDOOR))
//}.generate()
//
//// -----------------------
//
//// e_loop:2
//cloudinary.url {
//publicId("sample")
//effect(Effect.loop(2))
//}.generate()
//
//// -----------------------
//
//// e_oil_paint:70
//cloudinary.url {
//publicId("sample")
//effect(Effect.oilPaint(70))
//}.generate()
//
//// -----------------------
//
//// e_outline,co_orange
//cloudinary.url {
//publicId("sample")
//effect(Effect.outline() {
//color(Color.ORANGE)
//})
//}.generate()
//
//// -----------------------
//
//// e_outline:fill,co_orange
//cloudinary.url {
//publicId("sample")
//effect(Effect.outline() {
//mode(Outline.FILL)
//color(Color.ORANGE)
//})
//}.generate()
//
//// -----------------------
//
//// e_outline:inner,co_orange
//cloudinary.url {
//publicId("sample")
//effect(Effect.outline() {
//mode(Outline.INNER)
//color(Color.ORANGE)
//})
//}.generate()
//
//// -----------------------
//
//// e_outline:inner_fill,co_orange
//cloudinary.url {
//publicId("sample")
//effect(Effect.outline() {
//mode(Outline.INNER_FILL)
//color(Color.ORANGE)
//})
//}.generate()
//
//// -----------------------
//
//// e_outline:outer,co_orange
//cloudinary.url {
//publicId("sample")
//effect(Effect.outline() {
//mode(Outline.OUTER)
//color(Color.ORANGE)
//})
//}.generate()
//
//// -----------------------
//
//// e_outline:outer:20:200
//cloudinary.url {
//publicId("sample")
//effect(Effect.outline(20, 200) {
//mode(Outline.OUTER)
//})
//}.generate()
//
//// -----------------------
//
//// e_outline:outer:20:200/e_red:50/e_sharpen/e_improve/o_70
//cloudinary.url {
//publicId("sample")
//effect(Effect.outline(20, 200) {
//mode(Outline.OUTER)
//})
//adjust(Adjust.red(50))
//adjust(Adjust.sharpen())
//adjust(Adjust.improve())
//adjust(Adjust.opacity(70))
//}.generate()
//
//// -----------------------
//
//// e_outline:outer:20:200/l_cloudinary_icon/e_red:50/e_sharpen/e_improve/o_70
//cloudinary.url {
//publicId("sample")
//effect(Effect.outline(20, 200) {
//mode(Outline.OUTER)
//})
//overlay(Layer.image("cloudinary_icon"))
//adjust(Adjust.red(50))
//adjust(Adjust.sharpen())
//adjust(Adjust.improve())
//adjust(Adjust.opacity(70))
//}.generate()
//
//// -----------------------
//
//// e_pixelate_faces:9
//cloudinary.url {
//publicId("sample")
//effect(Effect.pixelateFaces(9))
//}.generate()
//
//// -----------------------
//
//// e_preview,fl_getinfo
//cloudinary.url {
//publicId("sample")
//effect(Effect.preview())
//addFlag(Flag.getinfo())
//}.generate()
//
//// -----------------------
//
//// e_preview:duration_12:max_seg_3:min_seg_dur_3
//cloudinary.url {
//publicId("sample")
//effect(Effect.preview(Duration12.maxSeg3(MaxSeg3.minSegDur3())))
//}.generate()
//
//// -----------------------
//
//// e_preview:duration_5
//cloudinary.url {
//publicId("sample")
//effect(Effect.preview(Preview.duration5()))
//}.generate()
//
//// -----------------------
//
//// e_preview:duration_8/c_crop,w_1600,h_800,e_loop
//cloudinary.url {
//publicId("sample")
//effect(Effect.preview(Preview.duration8()))
//effect(Effect.loop())
//resize(Resize.crop(1600, 800))
//}.generate()
//
//// -----------------------
//
//// e_replace_color:maroon:80:2b38aa
//cloudinary.url {
//publicId("sample")
//adjust(Adjust.replaceColor(80.2b38aa()))
//}.generate()
//
//// -----------------------
//
//// e_replace_color:maroon:80:2b38aa/w_250
//cloudinary.url {
//publicId("sample")
//adjust(Adjust.replaceColor(80.2b38aa()))
//resize(Resize.scale(250))
//}.generate()
//
//// -----------------------
//
//// e_saturation:50
//cloudinary.url {
//publicId("sample")
//adjust(Adjust.saturation(50))
//}.generate()
//
//// -----------------------
//
//// e_screen,l_cloudinary_icon
//cloudinary.url {
//publicId("sample")
//overlay(Layer.image("cloudinary_icon"), , BlendMode.screen())
//}.generate()
//
//// -----------------------
//
//// e_shadow:50,x_10,y_10
//cloudinary.url {
//publicId("sample")
//effect(Effect.shadow(50))
//gravity(Gravity.absolute(10, 10))
//}.generate()
//
//// -----------------------
//
//// e_sharpen
//cloudinary.url {
//publicId("sample")
//adjust(Adjust.sharpen())
//}.generate()
//
//// -----------------------
//
//// e_simulate_colorblind:deuteranopia
//cloudinary.url {
//publicId("sample")
//effect(Effect.simulateColorblind(SimulateColorblind.deuteranopia()))
//}.generate()
//
//// -----------------------
//
//// e_style_transfer,l_davinci_mona_lisa
//cloudinary.url {
//publicId("sample")
//effect(Effect.styleTransfer() {
//overlay(Layer.image("davinci_mona_lisa"))
//})
//}.generate()
//
//// -----------------------
//
//// e_tint:100:red:blue:yellow
//cloudinary.url {
//publicId("sample")
//adjust(Adjust.tint(100.red(Blue.yellow())))
//}.generate()
//
//// -----------------------
//
//// e_tint:20
//cloudinary.url {
//publicId("sample")
//adjust(Adjust.tint(20))
//}.generate()
//
//// -----------------------
//
//// e_tint:equalize:80:red:50p:blue:60p:yellow:40p
//cloudinary.url {
//publicId("sample")
//adjust(Adjust.tint(Yellow.40p()))
//}.generate()
//
//// -----------------------
//
//// e_tint:equalize:80:red:blue:yellow
//cloudinary.url {
//publicId("sample")
//adjust(Adjust.tint(Blue.yellow()))
//}.generate()
//
//// -----------------------
//
//// e_viesus_correct
//cloudinary.url {
//publicId("sample")
//adjust(Adjust.viesusCorrect())
//}.generate()
//
//// -----------------------
//
//// f_auto
//cloudinary.url {
//publicId("sample")
//format(Format.auto())
//}.generate()
//
//// -----------------------
//
//// f_auto,dpr_2,q_auto,h_150,bo_1px_solid_gray
//cloudinary.url {
//publicId("sample")
//border(Border.solid() {
//width(1)
//color(Color.GRAY)
//})
//resize(Resize.scale() {
//height(150)
//})
//format(Format.auto())
//quality(Quality.auto())
//dpr(2)
//}.generate()
//
//// -----------------------
//
//// f_auto,dpr_2,q_auto,h_200,bo_1px_solid_gray
//cloudinary.url {
//publicId("sample")
//border(Border.solid() {
//width(1)
//color(Color.GRAY)
//})
//resize(Resize.scale() {
//height(200)
//})
//format(Format.auto())
//quality(Quality.auto())
//dpr(2)
//}.generate()
//
//// -----------------------
//
//// f_auto,dpr_auto
//cloudinary.url {
//publicId("sample")
//format(Format.auto())
//dpr(Dpr.auto())
//}.generate()
//
//// -----------------------
//
//// f_auto,q_auto
//cloudinary.url {
//publicId("sample")
//format(Format.auto())
//quality(Quality.auto())
//}.generate()
//
//// -----------------------
//
//// f_auto,q_auto,dpr_2
//cloudinary.url {
//publicId("sample")
//format(Format.auto())
//quality(Quality.auto())
//dpr(2)
//}.generate()
//
//// -----------------------
//
//// f_auto,q_auto,dpr_2,h_150,bo_1px_solid_gray
//cloudinary.url {
//publicId("sample")
//border(Border.solid() {
//width(1)
//color(Color.GRAY)
//})
//resize(Resize.scale() {
//height(150)
//})
//format(Format.auto())
//quality(Quality.auto())
//dpr(2)
//}.generate()
//
//// -----------------------
//
//// f_auto,q_auto,dpr_2,h_200,bo_1px_solid_gray
//cloudinary.url {
//publicId("sample")
//border(Border.solid() {
//width(1)
//color(Color.GRAY)
//})
//resize(Resize.scale() {
//height(200)
//})
//format(Format.auto())
//quality(Quality.auto())
//dpr(2)
//}.generate()
//
//// -----------------------
//
//// f_auto,q_auto,dpr_2/bo_1px_solid_gray/w_500
//cloudinary.url {
//publicId("sample")
//quality(Quality.auto())
//dpr(2)
//border(Border.solid() {
//width(1)
//color(Color.GRAY)
//})
//resize(Resize.scale(500))
//}.generate()
//
//// -----------------------
//
//// f_auto,q_auto,dpr_auto,w_300
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(300))
//format(Format.auto())
//quality(Quality.auto())
//dpr(Dpr.auto())
//}.generate()
//
//// -----------------------
//
//// f_mp4,w_300
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(300))
//format(Format.mp4())
//}.generate()
//
//// -----------------------
//
//// f_webm
//cloudinary.url {
//publicId("sample")
//format(Format.webm())
//}.generate()
//
//// -----------------------
//
//// fl_attachment:pretty_flower,f_auto
//cloudinary.url {
//publicId("sample")
//format(Format.auto())
//addFlag(Flag.attachment("pretty_flower"))
//}.generate()
//
//// -----------------------
//
//// fl_awebp
//cloudinary.url {
//publicId("sample")
//addFlag(Flag.animatedWebP())
//}.generate()
//
//// -----------------------
//
//// fl_lossy
//cloudinary.url {
//publicId("sample")
//addFlag(Flag.lossy())
//}.generate()
//
//// -----------------------
//
//// fl_lossy,q_50
//cloudinary.url {
//publicId("sample")
//quality(50)
//addFlag(Flag.lossy())
//}.generate()
//
//// -----------------------
//
//// fl_region_relative,g_adv_eyes,l_glasses,w_1.5
//cloudinary.url {
//publicId("sample")
//overlay(Layer.image("glasses") {
//position(Position.advancedEyes())
//resize(Resize.scale(1.5) {
//resizeMode(ResizeMode.regionRelative())
//})
//})
//}.generate()
//
//// -----------------------
//
//// fl_region_relative,g_adv_eyes,l_glasses,w_1.7
//cloudinary.url {
//publicId("sample")
//overlay(Layer.image("glasses") {
//position(Position.advancedEyes())
//resize(Resize.scale(1.7) {
//resizeMode(ResizeMode.regionRelative())
//})
//})
//}.generate()
//
//// -----------------------
//
//// fl_region_relative,g_adv_faces,l_silver_face_mask,w_1.1
//cloudinary.url {
//publicId("sample")
//overlay(Layer.image("silver_face_mask") {
//resize(Resize.scale(1.1) {
//gravity(Gravity.advFaces())
//resizeMode(ResizeMode.regionRelative())
//})
//})
//}.generate()
//
//// -----------------------
//
//// fn_pre:remote:aHR0cHM6Ly9teS5wcmVwcm9jZXNzLmN1c3RvbS9mdW5jdGlvbg==
//cloudinary.url {
//publicId("sample")
//customFunction(CustomFunction.preprocessRemote("https://my.preprocess.custom/function"))
//}.generate()
//
//// -----------------------
//
//// fn_remote:aHR0cHM6Ly9teS5leGFtcGxlLmN1c3RvbS9mdW5jdGlvbg==
//cloudinary.url {
//publicId("sample")
//customFunction(CustomFunction.remote("https://my.example.custom/function"))
//}.generate()
//
//// -----------------------
//
//// fn_wasm:my_example.wasm
//cloudinary.url {
//publicId("sample")
//customFunction(CustomFunction.wasm("my_example.wasm"))
//}.generate()
//
//// -----------------------
//
//// g_auto:subject,c_fill,ar_0.7,w_750
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(750) {
//aspectRatio(0.7)
//gravity(Gravity.auto(ObjectGravity.SUBJECT))
//})
//}.generate()
//
//// -----------------------
//
//// g_face,c_crop
//cloudinary.url {
//publicId("sample")
//resize(Resize.crop() {
//gravity(Gravity.face())
//})
//}.generate()
//
//// -----------------------
//
//// g_face,c_crop,z_1.3
//cloudinary.url {
//publicId("sample")
//resize(Resize.crop() {
//gravity(Gravity.face())
//})
//zoom(1.3)
//}.generate()
//
//// -----------------------
//
//// g_face,c_thumb,w_150,h_150
//cloudinary.url {
//publicId("sample")
//resize(Resize.thumbnail(150, 150) {
//gravity(Gravity.face())
//})
//}.generate()
//
//// -----------------------
//
//// g_face,c_thumb,w_150,h_150,z_0.7
//cloudinary.url {
//publicId("sample")
//resize(Resize.thumbnail(150, 150) {
//gravity(Gravity.face())
//})
//zoom(0.7)
//}.generate()
//
//// -----------------------
//
//// g_face,w_500,c_thumb,fl_getinfo
//cloudinary.url {
//publicId("sample")
//resize(Resize.thumbnail(500) {
//gravity(Gravity.face())
//})
//addFlag(Flag.getinfo())
//}.generate()
//
//// -----------------------
//
//// g_north,l_text:arial_60:watchme,y_20
//cloudinary.url {
//publicId("sample")
//overlay(Layer.text("watchme") {
//fontFamily("arial")
//fontSize(60)
//}, Position.north() {
//y(20)
//})
//}.generate()
//
//// -----------------------
//
//// h_100,w_190
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(190, 100))
//}.generate()
//
//// -----------------------
//
//// h_105/e_shadow:50,x_10,y_10
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale() {
//height(105)
//})
//effect(Effect.shadow(50))
//gravity(Gravity.absolute(10, 10))
//}.generate()
//
//// -----------------------
//
//// h_120
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale() {
//height(120)
//})
//}.generate()
//
//// -----------------------
//
//// h_150
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale() {
//height(150)
//})
//}.generate()
//
//// -----------------------
//
//// h_150,fl_ignore_aspect_ratio
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale() {
//height(150)
//})
//addFlag(Flag.ignoreAspectRatio())
//}.generate()
//
//// -----------------------
//
//// h_200
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale() {
//height(200)
//})
//}.generate()
//
//// -----------------------
//
//// h_200,c_scale/e_outline:15:200,co_orange
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale() {
//height(200)
//})
//effect(Effect.outline(15, 200) {
//color(Color.ORANGE)
//})
//}.generate()
//
//// -----------------------
//
//// h_200,w_200,c_pad,b_auto:border
//cloudinary.url {
//publicId("sample")
//resize(Resize.pad(200, 200) {
//background(AutoBackground.border())
//})
//}.generate()
//
//// -----------------------
//
//// h_200,w_200,c_pad,b_auto:border_contrast
//cloudinary.url {
//publicId("sample")
//resize(Resize.pad(200, 200) {
//background(AutoBackground.borderContrast())
//})
//}.generate()
//
//// -----------------------
//
//// h_200,w_200,c_pad,b_auto:predominant
//cloudinary.url {
//publicId("sample")
//resize(Resize.pad(200, 200) {
//background(AutoBackground.predominant())
//})
//}.generate()
//
//// -----------------------
//
//// h_200,w_200,c_pad,b_auto:predominant_contrast
//cloudinary.url {
//publicId("sample")
//resize(Resize.pad(200, 200) {
//background(AutoBackground.predominantContrast())
//})
//}.generate()
//
//// -----------------------
//
//// h_200,w_500,fl_waveform
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500, 200))
//addFlag(Flag.waveform())
//}.generate()
//
//// -----------------------
//
//// h_200,w_500,fl_waveform,co_black,b_white
//cloudinary.url {
//publicId("sample")
//color(Color.BLACK)
//resize(Resize.scale(500, 200))
//background(Color.WHITE)
//addFlag(Flag.waveform())
//}.generate()
//
//// -----------------------
//
//// h_200/u_docs:transparent_grid,w_1.0,h_1.0,fl_relative,c_crop,o_50
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale() {
//height(200)
//})
//underlay(Layer.image("docs/transparent_grid") {
//adjust(Adjust.opacity(50))
//resize(Resize.crop(1.0, 1.0))
//addFlag(Flag.relative())
//})
//}.generate()
//
//// -----------------------
//
//// h_225/q_auto,f_auto,bo_1px_solid_black
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale() {
//height(225)
//})
//border(Border.solid() {
//width(1)
//color(Color.BLACK)
//})
//format(Format.auto())
//quality(Quality.auto())
//}.generate()
//
//// -----------------------
//
//// h_250
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale() {
//height(250)
//})
//}.generate()
//
//// -----------------------
//
//// h_250,w_150,c_fill,g_auto/f_auto/q_auto
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(150, 250) {
//gravity(Gravity.auto())
//})
//quality(Quality.auto())
//}.generate()
//
//// -----------------------
//
//// h_250,w_400,fl_waveform,so_2,eo_4,co_blue,b_transparent
//cloudinary.url {
//publicId("sample")
//trim(Trim.range(2, 4))
//color(Color.BLUE)
//resize(Resize.scale(400, 250))
//background(Color.TRANSPARENT)
//addFlag(Flag.waveform())
//}.generate()
//
//// -----------------------
//
//// h_300
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale() {
//height(300)
//})
//}.generate()
//
//// -----------------------
//
//// h_300,w_300
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(300, 300))
//}.generate()
//
//// -----------------------
//
//// h_300,w_300,c_pad,b_auto:predominant
//cloudinary.url {
//publicId("sample")
//resize(Resize.pad(300, 300) {
//background(AutoBackground.predominant())
//})
//}.generate()
//
//// -----------------------
//
//// h_300,w_300,c_pad,b_auto:predominant/e_gradient_fade:symmetric_pad,x_0.5
//cloudinary.url {
//publicId("sample")
//resize(Resize.pad(300, 300) {
//background(AutoBackground.predominant())
//})
//effect(Effect.gradientFade(GradientFade.symmetricPad()))
//gravity(Gravity.absolute(0.5))
//}.generate()
//
//// -----------------------
//
//// h_300,w_300,c_pad,b_auto:predominant_gradient:2:diagonal_desc
//cloudinary.url {
//publicId("sample")
//resize(Resize.pad(300, 300) {
//background(AutoBackground.predominantGradient:2:diagonalDesc())
//})
//}.generate()
//
//// -----------------------
//
//// h_300,w_300,c_pad,b_auto:predominant_gradient:4:palette_red_green_blue_orange
//cloudinary.url {
//publicId("sample")
//resize(Resize.pad(300, 300) {
//background(AutoBackground.predominantGradient:4:paletteRedGreenBlueOrange())
//})
//}.generate()
//
//// -----------------------
//
//// h_300,w_300,c_pad,b_auto:predominant_gradient_contrast:4
//cloudinary.url {
//publicId("sample")
//resize(Resize.pad(300, 300) {
//background(AutoBackground.predominantGradientContrast:4())
//})
//}.generate()
//
//// -----------------------
//
//// h_310
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale() {
//height(310)
//})
//}.generate()
//
//// -----------------------
//
//// h_320,c_scale/c_pad,h_320,w_480,b_blurred:400:15/e_volume:mute/e_accelerate:100
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale() {
//height(320)
//})
//resize(Resize.pad(480, 320) {
//background(Color.BLURRED {
//intensity(400)
//brightness(15)
//})
//})
//effect(Effect.volume(Volume.mute()))
//effect(Effect.accelerate(100))
//}.generate()
//
//// -----------------------
//
//// h_320,w_480
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(480, 320))
//}.generate()
//
//// -----------------------
//
//// h_350/q_auto,dpr_auto
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale() {
//height(350)
//})
//quality(Quality.auto())
//dpr(Dpr.auto())
//}.generate()
//
//// -----------------------
//
//// h_375/u_docs:bg_beach,g_south,w_800
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale() {
//height(375)
//})
//underlay(Layer.image("docs/bg_beach") {
//resize(Resize.scale(800) {
//gravity(Gravity.south())
//})
//})
//}.generate()
//
//// -----------------------
//
//// h_375/u_docs:bg_beach,g_south,w_800/h_200
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale() {
//height(375)
//})
//underlay(Layer.image("docs/bg_beach") {
//resize(Resize.scale(800) {
//gravity(Gravity.south())
//})
//})
//resize(Resize.scale() {
//height(200)
//})
//}.generate()
//
//// -----------------------
//
//// h_375/u_docs:rainbow_field,g_south,w_800
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale() {
//height(375)
//})
//underlay(Layer.image("docs/rainbow_field") {
//resize(Resize.scale(800) {
//gravity(Gravity.south())
//})
//})
//}.generate()
//
//// -----------------------
//
//// h_375/u_docs:rainbow_field,g_south,w_800/h_200
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale() {
//height(375)
//})
//underlay(Layer.image("docs/rainbow_field") {
//resize(Resize.scale(800) {
//gravity(Gravity.south())
//})
//})
//resize(Resize.scale() {
//height(200)
//})
//}.generate()
//
//// -----------------------
//
//// h_400,w_244,c_fill
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(244, 400))
//}.generate()
//
//// -----------------------
//
//// h_400,w_244,c_fill,so_1
//cloudinary.url {
//publicId("sample")
//trim(Trim.range(1))
//resize(Resize.fill(244, 400))
//}.generate()
//
//// -----------------------
//
//// h_400,w_244,g_auto,c_fill
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(244, 400) {
//gravity(Gravity.auto())
//})
//}.generate()
//
//// -----------------------
//
//// h_500,w_500,c_fill
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(500, 500))
//}.generate()
//
//// -----------------------
//
//// if_!sale:in_stock!_in_tags,l_sale_icon,w_180,g_south_east,x_30,y_30/w_250
//cloudinary.url {
//publicId("sample")
//ifCondition("!sale:in stock! in tags")
//overlay(Layer.image("sale_icon") {
//resize(Resize.scale(180) {
//gravity(Gravity.southEast(30, 30))
//})
//})
//endIfCondition()
//resize(Resize.scale(250))
//}.generate()
//
//// -----------------------
//
//// if_ar_gt_0.65_and_w_gt_1000,ar_0.65,h_1000,c_fill
//cloudinary.url {
//publicId("sample")
//ifCondition("ar > 0.65 and w > 1000")
//resize(Resize.fill() {
//height(1000)
//aspectRatio(0.65)
//})
//endIfCondition()
//}.generate()
//
//// -----------------------
//
//// if_ar_gt_1.2,c_pad,h_320,w_480,b_black/if_else,c_pad,h_320,w_480,b_blurred:400:15
//cloudinary.url {
//publicId("sample")
//ifCondition("ar > 1.2")
//resize(Resize.pad(480, 320) {
//background(Color.BLACK)
//})
//ifElse()
//resize(Resize.pad(480, 320) {
//background(Color.BLURRED {
//intensity(400)
//brightness(15)
//})
//})
//endIfCondition()
//}.generate()
//
//// -----------------------
//
//// if_ar_gt_1.2,c_pad,h_320,w_480,b_green/if_else,c_pad,h_320,w_480,b_blurred:400:15
//cloudinary.url {
//publicId("sample")
//ifCondition("ar > 1.2")
//resize(Resize.pad(480, 320) {
//background(Color.GREEN)
//})
//ifElse()
//resize(Resize.pad(480, 320) {
//background(Color.BLURRED {
//intensity(400)
//brightness(15)
//})
//})
//endIfCondition()
//}.generate()
//
//// -----------------------
//
//// if_ar_gt_3:4_and_w_gt_300_and_h_gt_200,c_crop,w_300,h_200
//cloudinary.url {
//publicId("sample")
//ifCondition("ar > 3:4 and w > 300 and h > 200")
//resize(Resize.crop(300, 200))
//endIfCondition()
//}.generate()
//
//// -----------------------
//
//// if_ar_lt_1.0,$height_300/if_else,$height_200/h_$height
//cloudinary.url {
//publicId("sample")
//ifCondition("ar < 1.0")
//variable("height", 300)
//ifElse()
//variable("height", 200)
//endIfCondition()
//resize(Resize.scale() {
//height("\$height")
//})
//}.generate()
//
//// -----------------------
//
//// if_ctx:!productType!_eq_!shoes!,w_200,ar_1:1,c_fill,g_auto
//cloudinary.url {
//publicId("sample")
//ifCondition("ctx:!productType! = !shoes!")
//resize(Resize.fill(200) {
//aspectRatio("1:1")
//gravity(Gravity.auto())
//})
//endIfCondition()
//}.generate()
//
//// -----------------------
//
//// if_ils_gt_0.5,w_120,h_150,c_pad/if_else,w_120,h_150,c_fill
//cloudinary.url {
//publicId("sample")
//ifCondition("ils > 0.5")
//resize(Resize.pad(120, 150))
//ifElse()
//resize(Resize.fill(120, 150))
//endIfCondition()
//}.generate()
//
//// -----------------------
//
//// if_w_gt_400,l_thumbs-up-small,w_150,g_north_east,x_10,y_10/w_400,c_limit
//cloudinary.url {
//publicId("sample")
//ifCondition("w > 400")
//overlay(Layer.image("thumbs-up-small") {
//resize(Resize.scale(150) {
//gravity(Gravity.northEast(10, 10))
//})
//})
//endIfCondition()
//resize(Resize.limitFit(400))
//}.generate()
//
//// -----------------------
//
//// if_w_lt_600/l_text:Arial_20:Image shown in full scale,co_white,g_south_east/c_scale,e_blur:400,u_small_dinosaur,w_600/if_end
//cloudinary.url {
//publicId("sample")
//ifCondition("w < 600")
//overlay(Layer.text("Image shown in full scale") {
//fontFamily("Arial")
//fontSize(20)
//color(Color.WHITE)
//}, Position.southEast())
//underlay(Layer.image("small_dinosaur") {
//effect(Effect.blur(400))
//resize(Resize.scale(600))
//})
//endIfCondition()
//}.generate()
//
//// -----------------------
//
//// if_w_lte_200,c_fill,h_120,w_80/if_else,c_fill,h_90,w_100
//cloudinary.url {
//publicId("sample")
//ifCondition("w <= 200")
//resize(Resize.fill(80, 120))
//ifElse()
//resize(Resize.fill(100, 90))
//endIfCondition()
//}.generate()
//
//// -----------------------
//
//// if_w_lte_400/c_fill,h_220,w_180/e_red/if_else/c_fill,h_190,w_300/e_oil_paint/if_end
//cloudinary.url {
//publicId("sample")
//ifCondition("w <= 400")
//resize(Resize.fill(180, 220))
//adjust(Adjust.red())
//ifElse()
//resize(Resize.fill(300, 190))
//effect(Effect.oilPaint())
//endIfCondition()
//}.generate()
//
//// -----------------------
//
//// l_call_text,fl_region_relative,w_1.1,g_ocr_text
//cloudinary.url {
//publicId("sample")
//overlay(Layer.image("call_text") {
//resize(Resize.scale(1.1) {
//gravity(Gravity.ocrText())
//resizeMode(ResizeMode.regionRelative())
//})
//})
//}.generate()
//
//// -----------------------
//
//// l_call_text,fl_region_relative,w_1.1,g_ocr_text/w_120
//cloudinary.url {
//publicId("sample")
//overlay(Layer.image("call_text") {
//resize(Resize.scale(1.1) {
//gravity(Gravity.ocrText())
//resizeMode(ResizeMode.regionRelative())
//})
//})
//resize(Resize.scale(120))
//}.generate()
//
//// -----------------------
//
//// l_cloudinary_icon
//cloudinary.url {
//publicId("sample")
//overlay(Layer.image("cloudinary_icon"))
//}.generate()
//
//// -----------------------
//
//// l_cloudinary_icon,g_north_west,y_20
//cloudinary.url {
//publicId("sample")
//overlay(Layer.image("cloudinary_icon"), Position.northWest() {
//y(20)
//})
//}.generate()
//
//// -----------------------
//
//// l_cloudinary_icon,so_6.5,eo_10,o_50,e_brightness:200
//cloudinary.url {
//publicId("sample")
//overlay(Layer.image("cloudinary_icon") {
//trim(Trim.range(6.5, 10))
//adjust(Adjust.brightness(200))
//adjust(Adjust.opacity(50))
//})
//}.generate()
//
//// -----------------------
//
//// l_cloudinary_icon,w_0.2,fl_relative
//cloudinary.url {
//publicId("sample")
//overlay(Layer.image("cloudinary_icon") {
//resize(Resize.scale(0.2))
//addFlag(Flag.relative())
//})
//}.generate()
//
//// -----------------------
//
//// l_cloudinary_icon,w_0.8,fl_relative
//cloudinary.url {
//publicId("sample")
//overlay(Layer.image("cloudinary_icon") {
//resize(Resize.scale(0.8))
//addFlag(Flag.relative())
//})
//}.generate()
//
//// -----------------------
//
//// l_cloudinary_icon,w_0.9,c_scale,g_north_east,o_70,e_brightness:50
//cloudinary.url {
//publicId("sample")
//overlay(Layer.image("cloudinary_icon") {
//adjust(Adjust.brightness(50))
//adjust(Adjust.opacity(70))
//resize(Resize.scale(0.9) {
//gravity(Gravity.northEast())
//})
//})
//}.generate()
//
//// -----------------------
//
//// l_cloudinary_icon,w_200,g_east,so_1,eo_11/l_text:arial_40:Sample Video,g_south,y_60,so_2
//cloudinary.url {
//publicId("sample")
//overlay(Layer.image("cloudinary_icon") {
//trim(Trim.range(1, 11))
//resize(Resize.scale(200) {
//gravity(Gravity.east())
//})
//})
//overlay(Layer.text("Sample Video") {
//fontFamily("arial")
//fontSize(40)
//trim(Trim.range(2))
//}, Position.south() {
//y(60)
//})
//}.generate()
//
//// -----------------------
//
//// l_cloudinary_icon,w_300,g_north_east,o_60
//cloudinary.url {
//publicId("sample")
//overlay(Layer.image("cloudinary_icon") {
//adjust(Adjust.opacity(60))
//resize(Resize.scale(300) {
//gravity(Gravity.northEast())
//})
//})
//}.generate()
//
//// -----------------------
//
//// l_couple/c_crop,g_faces,w_120,h_150/r_max/fl_layer_apply,g_north_east/l_cloudinary_icon,w_0.8,fl_relative,o_50,e_brightness:100/l_text:impact_40_bold:Sample image,g_south,y_20
//cloudinary.url {
//publicId("sample")
//overlay(Layer.image("couple") {
//resize(Resize.crop(120, 150) {
//gravity(Gravity.faces())
//})
//roundCorners(RoundCorners.max())
//}, Position.northEast())
//overlay(Layer.image("cloudinary_icon") {
//adjust(Adjust.brightness(100))
//adjust(Adjust.opacity(50))
//resize(Resize.scale(0.8))
//addFlag(Flag.relative())
//})
//overlay(Layer.text("Sample image") {
//fontFamily("impact")
//fontSize(40)
//fontWeight(FontWeight.BOLD)
//}, Position.south() {
//y(20)
//})
//}.generate()
//
//// -----------------------
//
//// l_face_left/w_200,h_200,c_thumb,g_face,r_max/fl_layer_apply,g_north_east
//cloudinary.url {
//publicId("sample")
//overlay(Layer.image("face_left") {
//roundCorners(RoundCorners.max())
//resize(Resize.thumbnail(200, 200) {
//gravity(Gravity.face())
//})
//}, Position.northEast())
//}.generate()
//
//// -----------------------
//
//// l_fetch:aHR0cDovL2Nsb3VkaW5hcnkuY29tL2ltYWdlcy9vbGRfbG9nby5wbmc=
//cloudinary.url {
//publicId("sample")
//overlay(Layer.image("fetch/aHR0cDovL2Nsb3VkaW5hcnkuY29tL2ltYWdlcy9vbGRfbG9nby5wbmc="))
//}.generate()
//
//// -----------------------
//
//// l_golden_star,g_faces,w_1.0,fl_region_relative
//cloudinary.url {
//publicId("sample")
//overlay(Layer.image("golden_star") {
//resize(Resize.scale(1.0) {
//gravity(Gravity.faces())
//resizeMode(ResizeMode.regionRelative())
//})
//})
//}.generate()
//
//// -----------------------
//
//// l_golden_star,g_faces,w_1.1,fl_region_relative
//cloudinary.url {
//publicId("sample")
//overlay(Layer.image("golden_star") {
//resize(Resize.scale(1.1) {
//gravity(Gravity.faces())
//resizeMode(ResizeMode.regionRelative())
//})
//})
//}.generate()
//
//// -----------------------
//
//// l_golden_star,g_north_west,w_20,x_314,y_148/w_500
//cloudinary.url {
//publicId("sample")
//overlay(Layer.image("golden_star") {
//resize(Resize.scale(20) {
//gravity(Gravity.northWest(314, 148))
//})
//})
//resize(Resize.scale(500))
//}.generate()
//
//// -----------------------
//
//// l_lut:iwltbap_aspen.3dl
//cloudinary.url {
//publicId("sample")
//add3dLut("iwltbap_aspen.3dl")
//}.generate()
//
//// -----------------------
//
//// l_lut:iwltbap_aspen.3dl/w_200
//cloudinary.url {
//publicId("sample")
//add3dLut("iwltbap_aspen.3dl")
//resize(Resize.scale(200))
//}.generate()
//
//// -----------------------
//
//// l_lut:iwltbap_sedona.3dl
//cloudinary.url {
//publicId("sample")
//add3dLut("iwltbap_sedona.3dl")
//}.generate()
//
//// -----------------------
//
//// l_lut:iwltbap_sedona.3dl/w_200
//cloudinary.url {
//publicId("sample")
//add3dLut("iwltbap_sedona.3dl")
//resize(Resize.scale(200))
//}.generate()
//
//// -----------------------
//
//// l_subtitles:arial_20:sample_sub_en.srt
//cloudinary.url {
//publicId("sample")
//overlay(Layer.subtitles("sample_sub_en.srt") {
//fontFamily("arial")
//fontSize(20)
//})
//}.generate()
//
//// -----------------------
//
//// l_subtitles:arial_70:sample_sub_en.srt,co_rgb:ffff00,b_black
//cloudinary.url {
//publicId("sample")
//overlay(Layer.subtitles("sample_sub_en.srt") {
//fontFamily("arial")
//fontSize(70)
//color(Color.rgb("#ffff00"))
//background(Color.BLACK)
//})
//}.generate()
//
//// -----------------------
//
//// l_subtitles:folder-permissions-tutorial.en-us.azure.transcript
//cloudinary.url {
//publicId("sample")
//overlay(Layer.subtitles("") {
//fontFamily("folder-permissions-tutorial.en-us.azure.transcript")
//})
//}.generate()
//
//// -----------------------
//
//// l_subtitles:impact_15:lincoln.transcript,co_khaki,b_rgb:331a00,g_south_west
//cloudinary.url {
//publicId("sample")
//overlay(Layer.subtitles("lincoln.transcript") {
//fontFamily("impact")
//fontSize(15)
//color(Color.KHAKI)
//background(Color.rgb("#331a00"))
//}, Position.southWest())
//}.generate()
//
//// -----------------------
//
//// l_subtitles:lincoln.transcript
//cloudinary.url {
//publicId("sample")
//overlay(Layer.subtitles("") {
//fontFamily("lincoln.transcript")
//})
//}.generate()
//
//// -----------------------
//
//// l_subtitles:sample_sub_en.srt
//cloudinary.url {
//publicId("sample")
//overlay(Layer.subtitles("") {
//fontFamily("sample")
//fontSize("sub")
//})
//}.generate()
//
//// -----------------------
//
//// l_subtitles:times_20:folder-permissions-tutorial.en-us.azure.transcript,co_blue,g_north
//cloudinary.url {
//publicId("sample")
//overlay(Layer.subtitles("folder-permissions-tutorial.en-us.azure.transcript") {
//fontFamily("times")
//fontSize(20)
//color(Color.BLUE)
//}, Position.north())
//}.generate()
//
//// -----------------------
//
//// l_text:AlexBrush-Regular.ttf_100:Happy New Year  2017 ,co_white,g_north_west,x_30,y_30
//cloudinary.url {
//publicId("sample")
//overlay(Layer.text("Happy New Year  2017 ") {
//fontFamily("AlexBrush-Regular.ttf")
//fontSize(100)
//color(Color.WHITE)
//}, Position.northWest(30, 30))
//}.generate()
//
//// -----------------------
//
//// l_text:arial_60:Cool Video,g_south,y_80,so_2,eo_5
//cloudinary.url {
//publicId("sample")
//overlay(Layer.text("Cool Video") {
//fontFamily("arial")
//fontSize(60)
//trim(Trim.range(2, 5))
//}, Position.south() {
//y(80)
//})
//}.generate()
//
//// -----------------------
//
//// l_text:impact_150:Your Brand Name or Logo Here,e_distort:arc:-120,co_white,g_south,y_840,o_60
//cloudinary.url {
//publicId("sample")
//overlay(Layer.text("Your Brand Name or Logo Here") {
//fontFamily("impact")
//fontSize(150)
//effect(Effect.distort(-120))
//adjust(Adjust.opacity(60))
//color(Color.WHITE)
//}, Position.south() {
//y(840)
//})
//}.generate()
//
//// -----------------------
//
//// l_video:dog,so_4.5,eo_8,a_90,w_200,g_east
//cloudinary.url {
//publicId("sample")
//overlay(Layer.video("dog") {
//trim(Trim.range(4.5, 8))
//resize(Resize.scale(200) {
//gravity(Gravity.east())
//})
//rotate(Rotate.angle(90))
//})
//}.generate()
//
//// -----------------------
//
//// l_video:kitten_fighting,e_fade:3000,w_100
//cloudinary.url {
//publicId("sample")
//overlay(Layer.video("kitten_fighting") {
//effect(Effect.fadeIn(3000))
//resize(Resize.scale(100))
//})
//}.generate()
//
//// -----------------------
//
//// l_video:ski_jump,fl_splice,e_reverse/l_video:ski_jump,fl_splice,e_accelerate:-50/e_brightness:10,r_max
//cloudinary.url {
//publicId("sample")
//concatenate(Layer.video("ski_jump") {
//effect(Effect.reverse())
//})
//concatenate(Layer.video("ski_jump") {
//effect(Effect.accelerate(-50))
//})
//roundCorners(RoundCorners.max())
//adjust(Adjust.brightness(10))
//}.generate()
//
//// -----------------------
//
//// l_video:ski_jump,fl_splice,e_reverse/l_video:ski_jump,fl_splice,e_accelerate:-50/e_volume:mute/e_brightness:10,r_max
//cloudinary.url {
//publicId("sample")
//concatenate(Layer.video("ski_jump") {
//effect(Effect.reverse())
//})
//concatenate(Layer.video("ski_jump") {
//effect(Effect.accelerate(-50))
//})
//effect(Effect.volume(Volume.mute()))
//roundCorners(RoundCorners.max())
//adjust(Adjust.brightness(10))
//}.generate()
//
//// -----------------------
//
//// o_30
//cloudinary.url {
//publicId("sample")
//adjust(Adjust.opacity(30))
//}.generate()
//
//// -----------------------
//
//// p_home_thing_
//cloudinary.url {
//publicId("sample")
//prefix("home_thing_")
//}.generate()
//
//// -----------------------
//
//// pg_2
//cloudinary.url {
//publicId("sample")
//getPage(2)
//}.generate()
//
//// -----------------------
//
//// pg_2-4;6;8/w_300
//cloudinary.url {
//publicId("sample")
//getPage("2-4;6;8")
//resize(Resize.scale(300))
//}.generate()
//
//// -----------------------
//
//// pg_embedded:3
//cloudinary.url {
//publicId("sample")
//getSmartObject(SmartObject.number(3))
//}.generate()
//
//// -----------------------
//
//// pg_embedded:3/w_300
//cloudinary.url {
//publicId("sample")
//getSmartObject(SmartObject.number(3))
//resize(Resize.scale(300))
//}.generate()
//
//// -----------------------
//
//// pg_embedded:name:skyline-3242040_1920.jpg
//cloudinary.url {
//publicId("sample")
//getSmartObject(SmartObject.names("skyline-3242040_1920.jpg"))
//}.generate()
//
//// -----------------------
//
//// pg_name:record_cover;Shadow/w_300
//cloudinary.url {
//publicId("sample")
//getLayer(PsdLayer.names("record_cover", "Shadow"))
//resize(Resize.scale(300))
//}.generate()
//
//// -----------------------
//
//// q_50
//cloudinary.url {
//publicId("sample")
//quality(50)
//}.generate()
//
//// -----------------------
//
//// q_60
//cloudinary.url {
//publicId("sample")
//quality(60)
//}.generate()
//
//// -----------------------
//
//// q_80
//cloudinary.url {
//publicId("sample")
//quality(80)
//}.generate()
//
//// -----------------------
//
//// q_auto
//cloudinary.url {
//publicId("sample")
//quality(Quality.auto())
//}.generate()
//
//// -----------------------
//
//// q_auto,dpr_2,f_auto
//cloudinary.url {
//publicId("sample")
//format(Format.auto())
//quality(Quality.auto())
//dpr(2)
//}.generate()
//
//// -----------------------
//
//// q_auto,dpr_2,f_auto,bo_1px_solid_black
//cloudinary.url {
//publicId("sample")
//border(Border.solid() {
//width(1)
//color(Color.BLACK)
//})
//format(Format.auto())
//quality(Quality.auto())
//dpr(2)
//}.generate()
//
//// -----------------------
//
//// q_auto,dpr_2,f_auto,bo_1px_solid_gray
//cloudinary.url {
//publicId("sample")
//border(Border.solid() {
//width(1)
//color(Color.GRAY)
//})
//format(Format.auto())
//quality(Quality.auto())
//dpr(2)
//}.generate()
//
//// -----------------------
//
//// q_auto,dpr_2,f_auto,h_300
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale() {
//height(300)
//})
//format(Format.auto())
//quality(Quality.auto())
//dpr(2)
//}.generate()
//
//// -----------------------
//
//// q_auto,dpr_2,h_300,f_auto,bo_1px_solid_black
//cloudinary.url {
//publicId("sample")
//border(Border.solid() {
//width(1)
//color(Color.BLACK)
//})
//resize(Resize.scale() {
//height(300)
//})
//format(Format.auto())
//quality(Quality.auto())
//dpr(2)
//}.generate()
//
//// -----------------------
//
//// q_auto,dpr_2,w_150,f_auto
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(150))
//format(Format.auto())
//quality(Quality.auto())
//dpr(2)
//}.generate()
//
//// -----------------------
//
//// q_auto,dpr_auto
//cloudinary.url {
//publicId("sample")
//quality(Quality.auto())
//dpr(Dpr.auto())
//}.generate()
//
//// -----------------------
//
//// q_auto,f_auto
//cloudinary.url {
//publicId("sample")
//format(Format.auto())
//quality(Quality.auto())
//}.generate()
//
//// -----------------------
//
//// q_auto,f_auto,bo_1px_solid_black
//cloudinary.url {
//publicId("sample")
//border(Border.solid() {
//width(1)
//color(Color.BLACK)
//})
//format(Format.auto())
//quality(Quality.auto())
//}.generate()
//
//// -----------------------
//
//// q_auto,f_auto,bo_1px_solid_gray,dpr_2,w_650
//cloudinary.url {
//publicId("sample")
//border(Border.solid() {
//width(1)
//color(Color.GRAY)
//})
//resize(Resize.scale(650))
//format(Format.auto())
//quality(Quality.auto())
//dpr(2)
//}.generate()
//
//// -----------------------
//
//// q_auto,f_auto,dpr_2
//cloudinary.url {
//publicId("sample")
//format(Format.auto())
//quality(Quality.auto())
//dpr(2)
//}.generate()
//
//// -----------------------
//
//// q_auto,f_auto,dpr_2,bo_1px_solid_gray
//cloudinary.url {
//publicId("sample")
//border(Border.solid() {
//width(1)
//color(Color.GRAY)
//})
//format(Format.auto())
//quality(Quality.auto())
//dpr(2)
//}.generate()
//
//// -----------------------
//
//// q_auto,f_auto,dpr_2,c_crop,w_100,h_80/w_40
//cloudinary.url {
//publicId("sample")
//resize(Resize.crop(100, 80))
//quality(Quality.auto())
//dpr(2)
//resize(Resize.scale(40))
//}.generate()
//
//// -----------------------
//
//// q_auto,f_auto,dpr_2,w_350,r_20/e_outline:1,co_black
//cloudinary.url {
//publicId("sample")
//roundCorners(20)
//resize(Resize.scale(350))
//quality(Quality.auto())
//dpr(2)
//effect(Effect.outline(1) {
//color(Color.BLACK)
//})
//}.generate()
//
//// -----------------------
//
//// q_auto,f_auto,dpr_2,w_400
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(400))
//format(Format.auto())
//quality(Quality.auto())
//dpr(2)
//}.generate()
//
//// -----------------------
//
//// q_auto,f_auto,dpr_2/bo_1px_solid_gray
//cloudinary.url {
//publicId("sample")
//quality(Quality.auto())
//dpr(2)
//border(Border.solid() {
//width(1)
//color(Color.GRAY)
//})
//}.generate()
//
//// -----------------------
//
//// q_auto,f_auto,dpr_auto
//cloudinary.url {
//publicId("sample")
//format(Format.auto())
//quality(Quality.auto())
//dpr(Dpr.auto())
//}.generate()
//
//// -----------------------
//
//// q_auto,f_auto,w_500
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//format(Format.auto())
//quality(Quality.auto())
//}.generate()
//
//// -----------------------
//
//// q_auto,w_600,f_auto,dpr_2
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(600))
//format(Format.auto())
//quality(Quality.auto())
//dpr(2)
//}.generate()
//
//// -----------------------
//
//// q_auto,w_800,f_auto
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(800))
//format(Format.auto())
//quality(Quality.auto())
//}.generate()
//
//// -----------------------
//
//// q_auto:best
//cloudinary.url {
//publicId("sample")
//quality(Quality.best())
//}.generate()
//
//// -----------------------
//
//// q_auto:eco
//cloudinary.url {
//publicId("sample")
//quality(Quality.eco())
//}.generate()
//
//// -----------------------
//
//// q_auto:good
//cloudinary.url {
//publicId("sample")
//quality(Quality.good())
//}.generate()
//
//// -----------------------
//
//// q_auto:low
//cloudinary.url {
//publicId("sample")
//quality(Quality.low())
//}.generate()
//
//// -----------------------
//
//// q_jpegmini
//cloudinary.url {
//publicId("sample")
//quality(Quality.jpegMini())
//}.generate()
//
//// -----------------------
//
//// r_20
//cloudinary.url {
//publicId("sample")
//roundCorners(20)
//}.generate()
//
//// -----------------------
//
//// so_0,du_3
//cloudinary.url {
//publicId("sample")
//trim(Trim.range(0) {
//duration(3)
//})
//}.generate()
//
//// -----------------------
//
//// so_1
//cloudinary.url {
//publicId("sample")
//trim(Trim.range(1))
//}.generate()
//
//// -----------------------
//
//// so_10p,du_30p
//cloudinary.url {
//publicId("sample")
//trim(Trim.range("10%") {
//duration("30%")
//})
//}.generate()
//
//// -----------------------
//
//// so_120
//cloudinary.url {
//publicId("sample")
//trim(Trim.range(120))
//}.generate()
//
//// -----------------------
//
//// so_21/bo_2px_solid_black
//cloudinary.url {
//publicId("sample")
//trim(Trim.range(21))
//border(Border.solid() {
//width(2)
//color(Color.BLACK)
//})
//}.generate()
//
//// -----------------------
//
//// so_4,w_350,h_350,c_crop,r_20,e_grayscale,bo_5px_solid_black
//cloudinary.url {
//publicId("sample")
//roundCorners(20)
//trim(Trim.range(4))
//effect(Effect.grayscale())
//border(Border.solid() {
//width(5)
//color(Color.BLACK)
//})
//resize(Resize.crop(350, 350))
//}.generate()
//
//// -----------------------
//
//// so_5
//cloudinary.url {
//publicId("sample")
//trim(Trim.range(5))
//}.generate()
//
//// -----------------------
//
//// so_6.5,eo_10
//cloudinary.url {
//publicId("sample")
//trim(Trim.range(6.5, 10))
//}.generate()
//
//// -----------------------
//
//// so_62
//cloudinary.url {
//publicId("sample")
//trim(Trim.range(62))
//}.generate()
//
//// -----------------------
//
//// so_8.5
//cloudinary.url {
//publicId("sample")
//trim(Trim.range(8.5))
//}.generate()
//
//// -----------------------
//
//// sp_hd
//cloudinary.url {
//publicId("sample")
//streamingProfile("hd")
//}.generate()
//
//// -----------------------
//
//// t_demo_combined
//cloudinary.url {
//publicId("sample")
//namedTransformation("demo_combined")
//}.generate()
//
//// -----------------------
//
//// t_fit_100x150
//cloudinary.url {
//publicId("sample")
//namedTransformation("fit_100x150")
//}.generate()
//
//// -----------------------
//
//// t_jpg_with_quality_30,w_100,h_50,c_fit
//cloudinary.url {
//publicId("sample")
//resize(Resize.fit(100, 50))
//namedTransformation("jpg_with_quality_30")
//}.generate()
//
//// -----------------------
//
//// t_jpg_with_quality_30/t_crop_400x400/t_fit_100x150
//cloudinary.url {
//publicId("sample")
//namedTransformation("jpg_with_quality_30")
//namedTransformation("crop_400x400")
//namedTransformation("fit_100x150")
//}.generate()
//
//// -----------------------
//
//// c_fill,w_100,h_100
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(100, 100))
//}.generate()
//
//// -----------------------
//
//// w_230,h_168,c_fit
//cloudinary.url {
//publicId("sample")
//resize(Resize.fit(230, 168))
//}.generate()
//
//// -----------------------
//
//// vc_auto
//cloudinary.url {
//publicId("sample")
//transcode(VideoCodec.auto())
//}.generate()
//
//// -----------------------
//
//// vc_h264:baseline:3.1
//cloudinary.url {
//publicId("sample")
//transcode(VideoCodec.h264(VideoCodecProfile.VCP_BASELINE, VideoCodecLevel.VCL_31))
//}.generate()
//
//// -----------------------
//
//// vs_1.1s,dl_200,w_200,e_loop:2
//cloudinary.url {
//publicId("sample")
//effect(Effect.loop(2))
//resize(Resize.scale(200))
//delay(200)
//videoSampling("1.1s")
//}.generate()
//
//// -----------------------
//
//// vs_10,dl_100,w_250,fl_awebp,fl_animated
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(250))
//delay(100)
//videoSampling(10)
//addFlag(Flag.awebp.animated())
//}.generate()
//
//// -----------------------
//
//// vs_40,dl_200,h_200,e_loop
//cloudinary.url {
//publicId("sample")
//effect(Effect.loop())
//resize(Resize.scale() {
//height(200)
//})
//delay(200)
//videoSampling(40)
//}.generate()
//
//// -----------------------
//
//// w_0.25,c_scale
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(0.25))
//}.generate()
//
//// -----------------------
//
//// w_0.4,a_20/l_cloudinary_icon,o_50,w_60,g_south_east,y_15,x_60
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(0.4))
//rotate(Rotate.angle(20))
//overlay(Layer.image("cloudinary_icon") {
//adjust(Adjust.opacity(50))
//resize(Resize.scale(60) {
//gravity(Gravity.southEast(60, 15))
//})
//})
//}.generate()
//
//// -----------------------
//
//// w_0.5
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(0.5))
//}.generate()
//
//// -----------------------
//
//// w_100,h_150,c_fill
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(100, 150))
//}.generate()
//
//// -----------------------
//
//// w_100,h_150,c_fill,g_faces
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(100, 150) {
//gravity(Gravity.faces())
//})
//}.generate()
//
//// -----------------------
//
//// w_100,h_40,c_thumb,g_adv_eyes
//cloudinary.url {
//publicId("sample")
//position(Position.advancedEyes())
//resize(Resize.thumbnail(100, 40))
//}.generate()
//
//// -----------------------
//
//// w_100/a_-20
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(100))
//rotate(Rotate.angle(-20))
//}.generate()
//
//// -----------------------
//
//// w_100/a_90
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(100))
//rotate(Rotate.angle(90))
//}.generate()
//
//// -----------------------
//
//// w_100/a_vflip.45
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(100))
//rotate(Rotate.angle(45) {
//flip("vertical_flip")
//})
//}.generate()
//
//// -----------------------
//
//// w_100/dpr_1.0
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(100))
//dpr(1.0)
//}.generate()
//
//// -----------------------
//
//// w_100/dpr_2.0
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(100))
//dpr(2.0)
//}.generate()
//
//// -----------------------
//
//// w_100/dpr_3.0
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(100))
//dpr(3.0)
//}.generate()
//
//// -----------------------
//
//// w_1000,dpr_2
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(1000))
//dpr(2)
//}.generate()
//
//// -----------------------
//
//// w_1000,f_auto,q_auto:best
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(1000))
//format(Format.auto())
//quality(Quality.best())
//}.generate()
//
//// -----------------------
//
//// w_115,h_135,c_thumb,g_faces
//cloudinary.url {
//publicId("sample")
//resize(Resize.thumbnail(115, 135) {
//gravity(Gravity.faces())
//})
//}.generate()
//
//// -----------------------
//
//// w_120
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(120))
//}.generate()
//
//// -----------------------
//
//// w_123/a_45
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(123))
//rotate(Rotate.angle(45))
//}.generate()
//
//// -----------------------
//
//// w_150,c_scale
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(150))
//}.generate()
//
//// -----------------------
//
//// w_150,e_art:al_dente
//cloudinary.url {
//publicId("sample")
//effect(Effect.artisticFilter(ArtisticFilter.AL_DENTE))
//resize(Resize.scale(150))
//}.generate()
//
//// -----------------------
//
//// w_150,e_art:athena
//cloudinary.url {
//publicId("sample")
//effect(Effect.artisticFilter(ArtisticFilter.ATHENA))
//resize(Resize.scale(150))
//}.generate()
//
//// -----------------------
//
//// w_150,e_art:audrey
//cloudinary.url {
//publicId("sample")
//effect(Effect.artisticFilter(ArtisticFilter.AUDREY))
//resize(Resize.scale(150))
//}.generate()
//
//// -----------------------
//
//// w_150,e_art:aurora
//cloudinary.url {
//publicId("sample")
//effect(Effect.artisticFilter(ArtisticFilter.AURORA))
//resize(Resize.scale(150))
//}.generate()
//
//// -----------------------
//
//// w_150,e_art:daguerre
//cloudinary.url {
//publicId("sample")
//effect(Effect.artisticFilter(ArtisticFilter.DAGUERRE))
//resize(Resize.scale(150))
//}.generate()
//
//// -----------------------
//
//// w_150,e_art:eucalyptus
//cloudinary.url {
//publicId("sample")
//effect(Effect.artisticFilter(ArtisticFilter.EUCALYPTUS))
//resize(Resize.scale(150))
//}.generate()
//
//// -----------------------
//
//// w_150,e_art:fes
//cloudinary.url {
//publicId("sample")
//effect(Effect.artisticFilter(ArtisticFilter.FES))
//resize(Resize.scale(150))
//}.generate()
//
//// -----------------------
//
//// w_150,e_art:frost
//cloudinary.url {
//publicId("sample")
//effect(Effect.artisticFilter(ArtisticFilter.FROST))
//resize(Resize.scale(150))
//}.generate()
//
//// -----------------------
//
//// w_150,e_art:hairspray
//cloudinary.url {
//publicId("sample")
//effect(Effect.artisticFilter(ArtisticFilter.HAIRSPRAY))
//resize(Resize.scale(150))
//}.generate()
//
//// -----------------------
//
//// w_150,e_art:hokusai
//cloudinary.url {
//publicId("sample")
//effect(Effect.artisticFilter(ArtisticFilter.HOKUSAI))
//resize(Resize.scale(150))
//}.generate()
//
//// -----------------------
//
//// w_150,e_art:incognito
//cloudinary.url {
//publicId("sample")
//effect(Effect.artisticFilter(ArtisticFilter.INCOGNITO))
//resize(Resize.scale(150))
//}.generate()
//
//// -----------------------
//
//// w_150,e_art:linen
//cloudinary.url {
//publicId("sample")
//effect(Effect.artisticFilter(ArtisticFilter.LINEN))
//resize(Resize.scale(150))
//}.generate()
//
//// -----------------------
//
//// w_150,e_art:peacock
//cloudinary.url {
//publicId("sample")
//effect(Effect.artisticFilter(ArtisticFilter.PEACOCK))
//resize(Resize.scale(150))
//}.generate()
//
//// -----------------------
//
//// w_150,e_art:primavera
//cloudinary.url {
//publicId("sample")
//effect(Effect.artisticFilter(ArtisticFilter.PRIMAVERA))
//resize(Resize.scale(150))
//}.generate()
//
//// -----------------------
//
//// w_150,e_art:quartz
//cloudinary.url {
//publicId("sample")
//effect(Effect.artisticFilter(ArtisticFilter.QUARTZ))
//resize(Resize.scale(150))
//}.generate()
//
//// -----------------------
//
//// w_150,e_art:red_rock
//cloudinary.url {
//publicId("sample")
//effect(Effect.artisticFilter(ArtisticFilter.RED_ROCK))
//resize(Resize.scale(150))
//}.generate()
//
//// -----------------------
//
//// w_150,e_art:refresh
//cloudinary.url {
//publicId("sample")
//effect(Effect.artisticFilter(ArtisticFilter.REFRESH))
//resize(Resize.scale(150))
//}.generate()
//
//// -----------------------
//
//// w_150,e_art:sizzle
//cloudinary.url {
//publicId("sample")
//effect(Effect.artisticFilter(ArtisticFilter.SIZZLE))
//resize(Resize.scale(150))
//}.generate()
//
//// -----------------------
//
//// w_150,e_art:sonnet
//cloudinary.url {
//publicId("sample")
//effect(Effect.artisticFilter(ArtisticFilter.SONNET))
//resize(Resize.scale(150))
//}.generate()
//
//// -----------------------
//
//// w_150,e_art:ukulele
//cloudinary.url {
//publicId("sample")
//effect(Effect.artisticFilter(ArtisticFilter.UKULELE))
//resize(Resize.scale(150))
//}.generate()
//
//// -----------------------
//
//// w_150,e_art:zorro
//cloudinary.url {
//publicId("sample")
//effect(Effect.artisticFilter(ArtisticFilter.ZORRO))
//resize(Resize.scale(150))
//}.generate()
//
//// -----------------------
//
//// w_150,h_100
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(150, 100))
//}.generate()
//
//// -----------------------
//
//// w_150,h_100,c_fill
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(150, 100))
//}.generate()
//
//// -----------------------
//
//// w_150,h_100,c_fill,r_10:40:25
//cloudinary.url {
//publicId("sample")
//roundCorners(10, 40, 25)
//resize(Resize.fill(150, 100))
//}.generate()
//
//// -----------------------
//
//// w_150,h_100,c_fill,r_25
//cloudinary.url {
//publicId("sample")
//roundCorners(25)
//resize(Resize.fill(150, 100))
//}.generate()
//
//// -----------------------
//
//// w_150,h_100,c_fill,r_25:0
//cloudinary.url {
//publicId("sample")
//roundCorners(25, 0)
//resize(Resize.fill(150, 100))
//}.generate()
//
//// -----------------------
//
//// w_150,h_100,c_fill,r_30:0:30:30
//cloudinary.url {
//publicId("sample")
//roundCorners(30, 0, 30, 30)
//resize(Resize.fill(150, 100))
//}.generate()
//
//// -----------------------
//
//// w_150,h_100,c_scale
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(150, 100))
//}.generate()
//
//// -----------------------
//
//// w_150,h_150,c_scale
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(150, 150))
//}.generate()
//
//// -----------------------
//
//// w_150,h_150,c_thumb,g_auto
//cloudinary.url {
//publicId("sample")
//resize(Resize.thumbnail(150, 150) {
//gravity(Gravity.auto())
//})
//}.generate()
//
//// -----------------------
//
//// w_150,h_150,c_thumb,g_auto:0
//cloudinary.url {
//publicId("sample")
//resize(Resize.thumbnail(150, 150) {
//gravity(Gravity.auto(ObjectGravity.0))
//})
//}.generate()
//
//// -----------------------
//
//// w_150,h_150,c_thumb,g_center
//cloudinary.url {
//publicId("sample")
//resize(Resize.thumbnail(150, 150) {
//gravity(Gravity.center())
//})
//}.generate()
//
//// -----------------------
//
//// w_150,h_150,c_thumb,g_face
//cloudinary.url {
//publicId("sample")
//resize(Resize.thumbnail(150, 150) {
//gravity(Gravity.face())
//})
//}.generate()
//
//// -----------------------
//
//// w_150,h_150,c_thumb,g_face,r_20,e_sepia/l_cloudinary_icon,g_south_east,x_5,y_5,w_50,o_60,e_brightness:200/a_10
//cloudinary.url {
//publicId("sample")
//roundCorners(20)
//effect(Effect.sepia())
//resize(Resize.thumbnail(150, 150) {
//gravity(Gravity.face())
//})
//overlay(Layer.image("cloudinary_icon") {
//adjust(Adjust.brightness(200))
//adjust(Adjust.opacity(60))
//resize(Resize.scale(50) {
//gravity(Gravity.southEast(5, 5))
//})
//})
//rotate(Rotate.angle(10))
//}.generate()
//
//// -----------------------
//
//// w_150,h_200,c_lfill
//cloudinary.url {
//publicId("sample")
//resize(Resize.limitFill(150, 200))
//}.generate()
//
//// -----------------------
//
//// w_150,h_60,c_fit
//cloudinary.url {
//publicId("sample")
//resize(Resize.fit(150, 60))
//}.generate()
//
//// -----------------------
//
//// w_1520,h_1440,c_crop,g_west,x_50
//cloudinary.url {
//publicId("sample")
//resize(Resize.crop(1520, 1440) {
//gravity(Gravity.west(50))
//})
//}.generate()
//
//// -----------------------
//
//// w_1520,h_1440,c_crop,g_west,x_50/e_pixelate_region:15,g_ocr_text
//cloudinary.url {
//publicId("sample")
//resize(Resize.crop(1520, 1440) {
//gravity(Gravity.west(50))
//})
//effect(Effect.pixelateRegion() {
//squareSize(15)
//gravity(Gravity.ocrText())
//})
//}.generate()
//
//// -----------------------
//
//// w_1520,h_1440,c_crop,g_west,x_50/h_283
//cloudinary.url {
//publicId("sample")
//resize(Resize.crop(1520, 1440) {
//gravity(Gravity.west(50))
//})
//resize(Resize.scale() {
//height(283)
//})
//}.generate()
//
//// -----------------------
//
//// w_20,q_auto
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(20))
//quality(Quality.auto())
//}.generate()
//
//// -----------------------
//
//// w_200
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(200))
//}.generate()
//
//// -----------------------
//
//// w_200,a_45/d_avatar.png
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(200))
//rotate(Rotate.angle(45))
//addGenericParam("default_image", "avatar.png")
//}.generate()
//
//// -----------------------
//
//// w_200,c_scale/e_outline:20:200,co_red/e_outline:15:200,co_orange/e_outline:10:200,co_yellow
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(200))
//effect(Effect.outline(20, 200) {
//color(Color.RED)
//})
//effect(Effect.outline(15, 200) {
//color(Color.ORANGE)
//})
//effect(Effect.outline(10, 200) {
//color(Color.YELLOW)
//})
//}.generate()
//
//// -----------------------
//
//// w_200,dpr_2.0
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(200))
//dpr(2.0)
//}.generate()
//
//// -----------------------
//
//// w_200,f_auto,q_auto,dpr_2
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(200))
//format(Format.auto())
//quality(Quality.auto())
//dpr(2)
//}.generate()
//
//// -----------------------
//
//// w_200,h_100
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(200, 100))
//}.generate()
//
//// -----------------------
//
//// w_200,h_150,c_crop
//cloudinary.url {
//publicId("sample")
//resize(Resize.crop(200, 150))
//}.generate()
//
//// -----------------------
//
//// w_200,h_150,c_crop,g_north_west
//cloudinary.url {
//publicId("sample")
//resize(Resize.crop(200, 150) {
//gravity(Gravity.northWest())
//})
//}.generate()
//
//// -----------------------
//
//// w_200,h_200,c_crop,g_auto
//cloudinary.url {
//publicId("sample")
//resize(Resize.crop(200, 200) {
//gravity(Gravity.auto())
//})
//}.generate()
//
//// -----------------------
//
//// w_200,h_200,c_crop,g_center
//cloudinary.url {
//publicId("sample")
//resize(Resize.crop(200, 200) {
//gravity(Gravity.center())
//})
//}.generate()
//
//// -----------------------
//
//// w_200,h_200,c_crop,r_max
//cloudinary.url {
//publicId("sample")
//roundCorners(RoundCorners.max())
//resize(Resize.crop(200, 200))
//}.generate()
//
//// -----------------------
//
//// w_200,h_200,c_fill
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(200, 200))
//}.generate()
//
//// -----------------------
//
//// w_200,h_200,c_fill,g_auto
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(200, 200) {
//gravity(Gravity.auto())
//})
//}.generate()
//
//// -----------------------
//
//// w_200,h_200,c_fill,g_center
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(200, 200) {
//gravity(Gravity.center())
//})
//}.generate()
//
//// -----------------------
//
//// w_200,h_200,c_fill,g_face
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(200, 200) {
//gravity(Gravity.face())
//})
//}.generate()
//
//// -----------------------
//
//// w_200,h_200,c_fill,g_faces
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(200, 200) {
//gravity(Gravity.faces())
//})
//}.generate()
//
//// -----------------------
//
//// w_200,h_200,c_fill,r_max/a_30/e_trim
//cloudinary.url {
//publicId("sample")
//roundCorners(RoundCorners.max())
//resize(Resize.fill(200, 200))
//rotate(Rotate.angle(30))
//effect(Effect.trim())
//}.generate()
//
//// -----------------------
//
//// w_200,h_200,c_fill/e_style_transfer,l_sailing_angel
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(200, 200))
//effect(Effect.styleTransfer() {
//overlay(Layer.image("sailing_angel"))
//})
//}.generate()
//
//// -----------------------
//
//// w_200,h_200,c_fit,a_hflip/l_cloudinary_icon,w_0.4,fl_relative,g_north,x_15,y_35,a_4,e_brightness:200,o_70
//cloudinary.url {
//publicId("sample")
//resize(Resize.fit(200, 200))
//rotate(Rotate.horizontalFlip())
//overlay(Layer.image("cloudinary_icon") {
//adjust(Adjust.brightness(200))
//adjust(Adjust.opacity(70))
//resize(Resize.scale(0.4) {
//gravity(Gravity.north(15, 35))
//})
//rotate(Rotate.angle(4))
//addFlag(Flag.relative())
//})
//}.generate()
//
//// -----------------------
//
//// w_200,h_200,c_thumb,g_face
//cloudinary.url {
//publicId("sample")
//resize(Resize.thumbnail(200, 200) {
//gravity(Gravity.face())
//})
//}.generate()
//
//// -----------------------
//
//// w_200,h_200,c_thumb,g_face,r_max
//cloudinary.url {
//publicId("sample")
//roundCorners(RoundCorners.max())
//resize(Resize.thumbnail(200, 200) {
//gravity(Gravity.face())
//})
//}.generate()
//
//// -----------------------
//
//// w_200,h_200,c_thumb,g_face,r_max,e_sharpen
//cloudinary.url {
//publicId("sample")
//roundCorners(RoundCorners.max())
//adjust(Adjust.sharpen())
//resize(Resize.thumbnail(200, 200) {
//gravity(Gravity.face())
//})
//}.generate()
//
//// -----------------------
//
//// w_200,h_200,c_thumb,g_face,r_max,l_face_left/fl_layer_apply,g_north_east
//cloudinary.url {
//publicId("sample")
//overlay(Layer.image("face_left") {
//roundCorners(RoundCorners.max())
//resize(Resize.thumbnail(200, 200) {
//gravity(Gravity.face())
//})
//}, Position.northEast())
//}.generate()
//
//// -----------------------
//
//// w_200,h_200,c_thumb,g_face/r_20,bo_5px_solid_black/l_cloudinary_icon,o_50,w_0.25,fl_relative,g_north_east,y_10,x_10
//cloudinary.url {
//publicId("sample")
//resize(Resize.thumbnail(200, 200) {
//gravity(Gravity.face())
//})
//roundCorners(20)
//border(Border.solid() {
//width(5)
//color(Color.BLACK)
//})
//overlay(Layer.image("cloudinary_icon") {
//adjust(Adjust.opacity(50))
//resize(Resize.scale(0.25) {
//gravity(Gravity.northEast(10, 10))
//})
//addFlag(Flag.relative())
//})
//}.generate()
//
//// -----------------------
//
//// w_200,h_300,c_fill,g_auto
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(200, 300) {
//gravity(Gravity.auto())
//})
//}.generate()
//
//// -----------------------
//
//// w_200,h_300,c_fill,g_center
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(200, 300) {
//gravity(Gravity.center())
//})
//}.generate()
//
//// -----------------------
//
//// w_200/dpr_1.0
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(200))
//dpr(1.0)
//}.generate()
//
//// -----------------------
//
//// w_200/dpr_2.0
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(200))
//dpr(2.0)
//}.generate()
//
//// -----------------------
//
//// w_200/dpr_3.0
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(200))
//dpr(3.0)
//}.generate()
//
//// -----------------------
//
//// w_200/l_text:Arial_30:Cool%F0%9F%98%8E
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(200))
//overlay(Layer.text("Cool%F0%9F%98%8E") {
//fontFamily("Arial")
//fontSize(30)
//})
//}.generate()
//
//// -----------------------
//
//// w_230,h_168,c_fit
//cloudinary.url {
//publicId("sample")
//resize(Resize.fit(230, 168))
//}.generate()
//
//// -----------------------
//
//// w_250
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(250))
//}.generate()
//
//// -----------------------
//
//// w_250,bo_1px_solid_gray
//cloudinary.url {
//publicId("sample")
//border(Border.solid() {
//width(1)
//color(Color.GRAY)
//})
//resize(Resize.scale(250))
//}.generate()
//
//// -----------------------
//
//// w_250,fl_animated,f_auto
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(250))
//format(Format.auto())
//addFlag(Flag.animated())
//}.generate()
//
//// -----------------------
//
//// w_250,h_150,c_fill,r_max
//cloudinary.url {
//publicId("sample")
//roundCorners(RoundCorners.max())
//resize(Resize.fill(250, 150))
//}.generate()
//
//// -----------------------
//
//// w_250,h_250,c_fill
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(250, 250))
//}.generate()
//
//// -----------------------
//
//// w_250,h_250,c_fill,g_east
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(250, 250) {
//gravity(Gravity.east())
//})
//}.generate()
//
//// -----------------------
//
//// w_250,h_250,c_fill,g_faces
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(250, 250) {
//gravity(Gravity.faces())
//})
//}.generate()
//
//// -----------------------
//
//// w_250,h_250,c_fill,g_north
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(250, 250) {
//gravity(Gravity.north())
//})
//}.generate()
//
//// -----------------------
//
//// w_250,h_250,c_fill,g_south_east
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(250, 250) {
//gravity(Gravity.southEast())
//})
//}.generate()
//
//// -----------------------
//
//// w_250,h_250,c_fit
//cloudinary.url {
//publicId("sample")
//resize(Resize.fit(250, 250))
//}.generate()
//
//// -----------------------
//
//// w_250,h_250,c_limit
//cloudinary.url {
//publicId("sample")
//resize(Resize.limitFit(250, 250))
//}.generate()
//
//// -----------------------
//
//// w_250,h_250,c_lpad,b_black
//cloudinary.url {
//publicId("sample")
//resize(Resize.limitPad(250, 250))
//background(Color.BLACK)
//}.generate()
//
//// -----------------------
//
//// w_250,h_250,c_mfit
//cloudinary.url {
//publicId("sample")
//resize(Resize.minimumFit(250, 250))
//}.generate()
//
//// -----------------------
//
//// w_250,h_250,c_mpad
//cloudinary.url {
//publicId("sample")
//resize(Resize.minimumPad(250, 250))
//}.generate()
//
//// -----------------------
//
//// w_250,h_250,c_pad,b_black
//cloudinary.url {
//publicId("sample")
//resize(Resize.pad(250, 250) {
//background(Color.BLACK)
//})
//}.generate()
//
//// -----------------------
//
//// w_250,q_80
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(250))
//quality(80)
//}.generate()
//
//// -----------------------
//
//// w_250,q_auto:best
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(250))
//quality(Quality.best())
//}.generate()
//
//// -----------------------
//
//// w_250,q_auto:eco
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(250))
//quality(Quality.eco())
//}.generate()
//
//// -----------------------
//
//// w_250,q_auto:good
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(250))
//quality(Quality.good())
//}.generate()
//
//// -----------------------
//
//// w_250,q_auto:low
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(250))
//quality(Quality.low())
//}.generate()
//
//// -----------------------
//
//// w_250,x_780,y_350,c_crop
//cloudinary.url {
//publicId("sample")
//resize(Resize.crop(250) {
//gravity(Gravity.absolute(780, 350))
//})
//}.generate()
//
//// -----------------------
//
//// w_250,x_780,y_350,c_crop/u_docs:transparent_grid,w_250,c_crop,o_50
//cloudinary.url {
//publicId("sample")
//resize(Resize.crop(250) {
//gravity(Gravity.absolute(780, 350))
//})
//underlay(Layer.image("docs/transparent_grid") {
//adjust(Adjust.opacity(50))
//resize(Resize.crop(250))
//})
//}.generate()
//
//// -----------------------
//
//// w_250,x_780,y_350,c_crop/w_1100
//cloudinary.url {
//publicId("sample")
//resize(Resize.crop(250) {
//gravity(Gravity.absolute(780, 350))
//})
//resize(Resize.scale(1100))
//}.generate()
//
//// -----------------------
//
//// w_250/u_docs:transparent_grid,w_1.0,h_1.0,fl_relative,c_crop,o_50
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(250))
//underlay(Layer.image("docs/transparent_grid") {
//adjust(Adjust.opacity(50))
//resize(Resize.crop(1.0, 1.0))
//addFlag(Flag.relative())
//})
//}.generate()
//
//// -----------------------
//
//// w_250/u_docs:transparent_grid,w_250,c_crop,o_50
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(250))
//underlay(Layer.image("docs/transparent_grid") {
//adjust(Adjust.opacity(50))
//resize(Resize.crop(250))
//})
//}.generate()
//
//// -----------------------
//
//// w_260,h_200,c_crop,g_north
//cloudinary.url {
//publicId("sample")
//resize(Resize.crop(260, 200) {
//gravity(Gravity.north())
//})
//}.generate()
//
//// -----------------------
//
//// w_28,q_auto
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(28))
//quality(Quality.auto())
//}.generate()
//
//// -----------------------
//
//// w_28,q_auto,f_auto,dpr_2
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(28))
//format(Format.auto())
//quality(Quality.auto())
//dpr(2)
//}.generate()
//
//// -----------------------
//
//// w_300
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(300))
//}.generate()
//
//// -----------------------
//
//// w_300,c_fill,g_auto,ar_1:1
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(300) {
//aspectRatio("1:1")
//gravity(Gravity.auto())
//})
//}.generate()
//
//// -----------------------
//
//// w_300,c_scale,pg_2
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(300))
//getPage(2)
//}.generate()
//
//// -----------------------
//
//// w_300,c_scale,pg_4,e_sepia,r_50,bo_2px_solid_rgb:999/l_aspose_cloud_logo,w_0.9,fl_relative,o_70,g_south,y_20
//cloudinary.url {
//publicId("sample")
//roundCorners(50)
//effect(Effect.sepia())
//border(Border.solid() {
//width(2)
//color(Color.rgb("#999"))
//})
//resize(Resize.scale(300))
//getPage(4)
//overlay(Layer.image("aspose_cloud_logo") {
//adjust(Adjust.opacity(70))
//resize(Resize.scale(0.9) {
//gravity(Gravity.south() {
//y(20)
//})
//})
//addFlag(Flag.relative())
//})
//}.generate()
//
//// -----------------------
//
//// w_300,e_accelerate:100
//cloudinary.url {
//publicId("sample")
//effect(Effect.accelerate(100))
//resize(Resize.scale(300))
//}.generate()
//
//// -----------------------
//
//// w_300,e_distort:40:25:280:60:260:155:35:165
//cloudinary.url {
//publicId("sample")
//effect(Effect.distort(165))
//resize(Resize.scale(300))
//}.generate()
//
//// -----------------------
//
//// w_300,e_grayscale
//cloudinary.url {
//publicId("sample")
//effect(Effect.grayscale())
//resize(Resize.scale(300))
//}.generate()
//
//// -----------------------
//
//// w_300,e_noise:50
//cloudinary.url {
//publicId("sample")
//effect(Effect.noise(50))
//resize(Resize.scale(300))
//}.generate()
//
//// -----------------------
//
//// w_300,f_auto,q_auto,dpr_2
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(300))
//format(Format.auto())
//quality(Quality.auto())
//dpr(2)
//}.generate()
//
//// -----------------------
//
//// w_300,fl_keep_iptc
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(300))
//addFlag(Flag.keepIptc())
//}.generate()
//
//// -----------------------
//
//// w_300,fl_png8
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(300))
//addFlag(Flag.png8())
//}.generate()
//
//// -----------------------
//
//// w_300,h_100,c_scale
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(300, 100))
//}.generate()
//
//// -----------------------
//
//// w_300,h_200,c_crop
//cloudinary.url {
//publicId("sample")
//resize(Resize.crop(300, 200))
//}.generate()
//
//// -----------------------
//
//// w_300,h_200,c_fill,dpr_auto
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(300, 200))
//dpr(Dpr.auto())
//}.generate()
//
//// -----------------------
//
//// w_300,h_200,c_fill/l_video:dog,fl_splice,w_300,h_200,c_fill
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(300, 200))
//concatenate(Layer.video("dog") {
//resize(Resize.fill(300, 200))
//})
//}.generate()
//
//// -----------------------
//
//// w_300,h_200,c_fill/l_video:dog,fl_splice,w_300,h_200,c_fill,so_0
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(300, 200))
//concatenate(Layer.video("dog") {
//trim(Trim.range(0))
//resize(Resize.fill(300, 200))
//})
//}.generate()
//
//// -----------------------
//
//// w_300,h_200,du_5/l_video:dog,w_300,h_200/du_5/l_video:transition1,e_transition/fl_layer_apply/fl_layer_apply
//cloudinary.url {
//publicId("sample")
//trim(Trim.duration(5))
//resize(Resize.scale(300, 200))
//overlay(Layer.video("dog") {
//resize(Resize.scale(300, 200))
//trim(Trim.duration(5))
//overlay(Layer.video("transition1") {
//effect(Effect.transition())
//})
//})
//}.generate()
//
//// -----------------------
//
//// w_300,h_200,du_5/l_video:dog,w_300,h_200/du_5/l_video:transition1,e_transition/fl_layer_apply/fl_layer_apply/l_video:snow_deer,w_300,h_200/du_5/l_video:transition2,e_transition/fl_layer_apply/fl_layer_apply/l_video:dog,fl_splice,w_300,h_200/du_5/fl_layer_apply
//cloudinary.url {
//publicId("sample")
//trim(Trim.duration(5))
//resize(Resize.scale(300, 200))
//overlay(Layer.video("dog") {
//resize(Resize.scale(300, 200))
//trim(Trim.duration(5))
//overlay(Layer.video("transition1") {
//effect(Effect.transition())
//addFlag(Flag.layerApply())
//addFlag(Flag.layerApply())
//overlay(Layer.video("snow_deer") {
//resize(Resize.scale(300, 200))
//trim(Trim.duration(5))
//overlay(Layer.video("transition2") {
//effect(Effect.transition())
//})
//})
//})
//concatenate(Layer.video("dog") {
//resize(Resize.scale(300, 200))
//})
//trim(Trim.duration(5))
//})
//}.generate()
//
//// -----------------------
//
//// w_300,h_200/w_300,h_200,l_sample,fl_splice,du_3/so_0,fl_layer_apply
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(300, 200))
//concatenate(Layer.image("sample") {
//trim(Trim.duration(3))
//resize(Resize.scale(300, 200))
//trim(Trim.range(0))
//})
//}.generate()
//
//// -----------------------
//
//// w_300,h_250,e_grayscale
//cloudinary.url {
//publicId("sample")
//effect(Effect.grayscale())
//resize(Resize.scale(300, 250))
//}.generate()
//
//// -----------------------
//
//// w_300,h_300,c_fill,b_blue,r_max
//cloudinary.url {
//publicId("sample")
//roundCorners(RoundCorners.max())
//resize(Resize.fill(300, 300))
//background(Color.BLUE)
//}.generate()
//
//// -----------------------
//
//// w_300,h_300,c_fill,g_face,r_max,f_auto
//cloudinary.url {
//publicId("sample")
//roundCorners(RoundCorners.max())
//resize(Resize.fill(300, 300) {
//gravity(Gravity.face())
//})
//format(Format.auto())
//}.generate()
//
//// -----------------------
//
//// w_300,h_300,c_fill,r_max,e_sharpen,so_30p/l_cloudinary_icon,w_0.9,fl_relative,o_40
//cloudinary.url {
//publicId("sample")
//roundCorners(RoundCorners.max())
//trim(Trim.range("30%"))
//adjust(Adjust.sharpen())
//resize(Resize.fill(300, 300))
//overlay(Layer.image("cloudinary_icon") {
//adjust(Adjust.opacity(40))
//resize(Resize.scale(0.9))
//addFlag(Flag.relative())
//})
//}.generate()
//
//// -----------------------
//
//// w_300,h_300,c_fill/l_cloudinary_icon,w_0.8,fl_relative,e_brightness:200,o_50
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(300, 300))
//overlay(Layer.image("cloudinary_icon") {
//adjust(Adjust.brightness(200))
//adjust(Adjust.opacity(50))
//resize(Resize.scale(0.8))
//addFlag(Flag.relative())
//})
//}.generate()
//
//// -----------------------
//
//// w_300,h_300,c_pad,b_blue
//cloudinary.url {
//publicId("sample")
//resize(Resize.pad(300, 300) {
//background(Color.BLUE)
//})
//}.generate()
//
//// -----------------------
//
//// w_300,h_300,c_pad,b_green
//cloudinary.url {
//publicId("sample")
//resize(Resize.pad(300, 300) {
//background(Color.GREEN)
//})
//}.generate()
//
//// -----------------------
//
//// w_300,h_350,c_fill,g_north
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(300, 350) {
//gravity(Gravity.north())
//})
//}.generate()
//
//// -----------------------
//
//// w_300,h_80,c_thumb,g_adv_eyes
//cloudinary.url {
//publicId("sample")
//position(Position.advancedEyes())
//resize(Resize.thumbnail(300, 80))
//}.generate()
//
//// -----------------------
//
//// w_300,pg_2
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(300))
//getPage(2)
//}.generate()
//
//// -----------------------
//
//// w_300,r_30
//cloudinary.url {
//publicId("sample")
//roundCorners(30)
//resize(Resize.scale(300))
//}.generate()
//
//// -----------------------
//
//// w_300,r_max
//cloudinary.url {
//publicId("sample")
//roundCorners(RoundCorners.max())
//resize(Resize.scale(300))
//}.generate()
//
//// -----------------------
//
//// w_300/c_fill,e_shadow,x_w_div_50,y_w_div_50
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(300))
//effect(Effect.shadow())
//resize(Resize.fill() {
//gravity(Gravity.absolute())
//})
//}.generate()
//
//// -----------------------
//
//// w_300/l_video:dog,w_100,g_north_east,r_max
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(300))
//overlay(Layer.video("dog") {
//roundCorners(RoundCorners.max())
//resize(Resize.scale(100) {
//gravity(Gravity.northEast())
//})
//})
//}.generate()
//
//// -----------------------
//
//// w_300/w_200,y_30,a_9,h_150,g_north,c_fit,l_text:Times_18_bold_center:Lorem ipsum dolor sit amet consectetur adipisicing elit sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(300))
//overlay(Layer.text("Lorem ipsum dolor sit amet consectetur adipisicing elit sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.") {
//fontFamily("Times")
//fontSize(18)
//fontWeight(FontWeight.BOLD)
//textAlignment(TextAlignment.CENTER)
//resize(Resize.fit(200, 150) {
//gravity(Gravity.north() {
//y(30)
//})
//})
//rotate(Rotate.angle(9))
//})
//}.generate()
//
//// -----------------------
//
//// w_350,bo_5px_solid_black/q_auto,dpr_auto
//cloudinary.url {
//publicId("sample")
//border(Border.solid() {
//width(5)
//color(Color.BLACK)
//})
//resize(Resize.scale(350))
//quality(Quality.auto())
//dpr(Dpr.auto())
//}.generate()
//
//// -----------------------
//
//// w_350,e_tint:100:red:blue:yellow
//cloudinary.url {
//publicId("sample")
//adjust(Adjust.tint(100.red(Blue.yellow())))
//resize(Resize.scale(350))
//}.generate()
//
//// -----------------------
//
//// w_350,e_tint:20
//cloudinary.url {
//publicId("sample")
//adjust(Adjust.tint(20))
//resize(Resize.scale(350))
//}.generate()
//
//// -----------------------
//
//// w_350,e_tint:equalize:80:red:50p:blue:60p:yellow:40p
//cloudinary.url {
//publicId("sample")
//adjust(Adjust.tint(Yellow.40p()))
//resize(Resize.scale(350))
//}.generate()
//
//// -----------------------
//
//// w_350,f_auto
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(350))
//format(Format.auto())
//}.generate()
//
//// -----------------------
//
//// w_350,f_gif
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(350))
//format(Format.gif())
//}.generate()
//
//// -----------------------
//
//// w_350,h_200,x_230,y_20,c_crop
//cloudinary.url {
//publicId("sample")
//resize(Resize.crop(350, 200) {
//gravity(Gravity.absolute(230, 20))
//})
//}.generate()
//
//// -----------------------
//
//// w_350,q_100
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(350))
//quality(100)
//}.generate()
//
//// -----------------------
//
//// w_350,q_80
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(350))
//quality(80)
//}.generate()
//
//// -----------------------
//
//// w_350,q_auto:best
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(350))
//quality(Quality.best())
//}.generate()
//
//// -----------------------
//
//// w_350,q_auto:eco
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(350))
//quality(Quality.eco())
//}.generate()
//
//// -----------------------
//
//// w_350,q_auto:good
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(350))
//quality(Quality.good())
//}.generate()
//
//// -----------------------
//
//// w_350,q_auto:low
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(350))
//quality(Quality.low())
//}.generate()
//
//// -----------------------
//
//// w_350/q_auto,dpr_auto
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(350))
//quality(Quality.auto())
//dpr(Dpr.auto())
//}.generate()
//
//// -----------------------
//
//// w_400
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(400))
//}.generate()
//
//// -----------------------
//
//// w_400,ar_1,c_thumb,g_auto:skateboard
//cloudinary.url {
//publicId("sample")
//resize(Resize.thumbnail(400) {
//aspectRatio(1)
//gravity(Gravity.auto(ObjectGravity.SKATEBOARD))
//})
//}.generate()
//
//// -----------------------
//
//// w_400,c_fill,ar_4:3
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(400) {
//aspectRatio("4:3")
//})
//}.generate()
//
//// -----------------------
//
//// w_400,f_png
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(400))
//format(Format.png())
//}.generate()
//
//// -----------------------
//
//// w_400,h_150,c_lpad,b_green
//cloudinary.url {
//publicId("sample")
//resize(Resize.limitPad(400, 150))
//background(Color.GREEN)
//}.generate()
//
//// -----------------------
//
//// w_400,h_250,c_fill,g_south/l_nice_couple,w_1.3,h_1.3,g_faces,c_crop,fl_region_relative/e_saturation:50/e_vignette/fl_layer_apply,w_100,r_max,g_center,y_20,x_-20/l_balloon,h_55/e_hue:-20,a_5/fl_layer_apply,x_30,y_5/l_text:Cookie_40_bold:Love,e_colorize,co_rgb:f08/a_20,fl_layer_apply,x_-45,y_44/c_crop,w_300,h_250,x_30/r_60
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(400, 250) {
//gravity(Gravity.south())
//})
//overlay(Layer.image("nice_couple") {
//resize(Resize.crop(1.3, 1.3) {
//gravity(Gravity.faces())
//resizeMode(ResizeMode.regionRelative())
//})
//adjust(Adjust.saturation(50))
//effect(Effect.vignette())
//roundCorners(RoundCorners.max())
//resize(Resize.scale(100) {
//gravity(Gravity.center(-20, 20))
//})
//addFlag(Flag.layerApply())
//overlay(Layer.image("balloon") {
//resize(Resize.scale() {
//height(55)
//})
//adjust(Adjust.hue(-20))
//rotate(Rotate.angle(5))
//}, Position.absolute(30, 5))
//overlay(Layer.text("Love") {
//fontFamily("Cookie")
//fontSize(40)
//fontWeight(FontWeight.BOLD)
//effect(Effect.colorize() {
//color(Color.rgb("#f08"))
//})
//})
//rotate(Rotate.angle(20))
//}, Position.absolute(-45, 44))
//resize(Resize.crop(300, 250) {
//gravity(Gravity.absolute(30))
//})
//roundCorners(60)
//}.generate()
//
//// -----------------------
//
//// w_400,h_300,c_pad
//cloudinary.url {
//publicId("sample")
//resize(Resize.pad(400, 300))
//}.generate()
//
//// -----------------------
//
//// w_400,h_400,c_crop,g_face,r_max/w_200
//cloudinary.url {
//publicId("sample")
//roundCorners(RoundCorners.max())
//resize(Resize.crop(400, 400) {
//gravity(Gravity.face())
//})
//resize(Resize.scale(200))
//}.generate()
//
//// -----------------------
//
//// w_450,bo_5px_solid_black/q_auto,dpr_auto
//cloudinary.url {
//publicId("sample")
//border(Border.solid() {
//width(5)
//color(Color.BLACK)
//})
//resize(Resize.scale(450))
//quality(Quality.auto())
//dpr(Dpr.auto())
//}.generate()
//
//// -----------------------
//
//// w_480,so_3,eo_5,e_boomerang/e_loop
//cloudinary.url {
//publicId("sample")
//trim(Trim.range(3, 5))
//effect(Effect.boomerang())
//resize(Resize.scale(480))
//effect(Effect.loop())
//}.generate()
//
//// -----------------------
//
//// w_500
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//}.generate()
//
//// -----------------------
//
//// w_500,ar_1,c_crop,g_auto:bottle
//cloudinary.url {
//publicId("sample")
//resize(Resize.crop(500) {
//aspectRatio(1)
//gravity(Gravity.auto(ObjectGravity.BOTTLE))
//})
//}.generate()
//
//// -----------------------
//
//// w_500,ar_1,c_crop,g_auto:bottle/h_150
//cloudinary.url {
//publicId("sample")
//resize(Resize.crop(500) {
//aspectRatio(1)
//gravity(Gravity.auto(ObjectGravity.BOTTLE))
//})
//resize(Resize.scale() {
//height(150)
//})
//}.generate()
//
//// -----------------------
//
//// w_500,ar_1,c_crop,g_bottle
//cloudinary.url {
//publicId("sample")
//resize(Resize.crop(500) {
//aspectRatio(1)
//gravity(Gravity.bottle())
//})
//}.generate()
//
//// -----------------------
//
//// w_500,ar_1,c_crop,g_bottle/h_150
//cloudinary.url {
//publicId("sample")
//resize(Resize.crop(500) {
//aspectRatio(1)
//gravity(Gravity.bottle())
//})
//resize(Resize.scale() {
//height(150)
//})
//}.generate()
//
//// -----------------------
//
//// w_500,ar_1,c_fill,g_auto:bottle
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(500) {
//aspectRatio(1)
//gravity(Gravity.auto(ObjectGravity.BOTTLE))
//})
//}.generate()
//
//// -----------------------
//
//// w_500,ar_1,c_fill,g_auto:bottle/h_150
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(500) {
//aspectRatio(1)
//gravity(Gravity.auto(ObjectGravity.BOTTLE))
//})
//resize(Resize.scale() {
//height(150)
//})
//}.generate()
//
//// -----------------------
//
//// w_500,ar_1,c_fill,g_bottle
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(500) {
//aspectRatio(1)
//gravity(Gravity.bottle())
//})
//}.generate()
//
//// -----------------------
//
//// w_500,ar_1,c_fill,g_bottle/h_150
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(500) {
//aspectRatio(1)
//gravity(Gravity.bottle())
//})
//resize(Resize.scale() {
//height(150)
//})
//}.generate()
//
//// -----------------------
//
//// w_500,ar_1,c_thumb,g_auto:bottle
//cloudinary.url {
//publicId("sample")
//resize(Resize.thumbnail(500) {
//aspectRatio(1)
//gravity(Gravity.auto(ObjectGravity.BOTTLE))
//})
//}.generate()
//
//// -----------------------
//
//// w_500,ar_1,c_thumb,g_auto:bottle/h_150
//cloudinary.url {
//publicId("sample")
//resize(Resize.thumbnail(500) {
//aspectRatio(1)
//gravity(Gravity.auto(ObjectGravity.BOTTLE))
//})
//resize(Resize.scale() {
//height(150)
//})
//}.generate()
//
//// -----------------------
//
//// w_500,ar_1,c_thumb,g_bottle
//cloudinary.url {
//publicId("sample")
//resize(Resize.thumbnail(500) {
//aspectRatio(1)
//gravity(Gravity.bottle())
//})
//}.generate()
//
//// -----------------------
//
//// w_500,ar_1,c_thumb,g_bottle/h_150
//cloudinary.url {
//publicId("sample")
//resize(Resize.thumbnail(500) {
//aspectRatio(1)
//gravity(Gravity.bottle())
//})
//resize(Resize.scale() {
//height(150)
//})
//}.generate()
//
//// -----------------------
//
//// w_500,ar_1.0,c_fill,g_auto
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(500) {
//aspectRatio(1.0)
//gravity(Gravity.auto())
//})
//}.generate()
//
//// -----------------------
//
//// w_500,ar_1.0,c_fill,g_auto/h_300
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(500) {
//aspectRatio(1.0)
//gravity(Gravity.auto())
//})
//resize(Resize.scale() {
//height(300)
//})
//}.generate()
//
//// -----------------------
//
//// w_500,ar_1.0,c_fill,g_auto:person_avoid
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(500) {
//aspectRatio(1.0)
//gravity(Gravity.auto(ObjectGravity.PERSON_AVOID))
//})
//}.generate()
//
//// -----------------------
//
//// w_500,ar_1.0,c_fill,g_auto:person_avoid/h_300
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(500) {
//aspectRatio(1.0)
//gravity(Gravity.auto(ObjectGravity.PERSON_AVOID))
//})
//resize(Resize.scale() {
//height(300)
//})
//}.generate()
//
//// -----------------------
//
//// w_500,bo_5px_solid_black
//cloudinary.url {
//publicId("sample")
//border(Border.solid() {
//width(5)
//color(Color.BLACK)
//})
//resize(Resize.scale(500))
//}.generate()
//
//// -----------------------
//
//// w_500,e_brightness:30
//cloudinary.url {
//publicId("sample")
//adjust(Adjust.brightness(30))
//resize(Resize.scale(500))
//}.generate()
//
//// -----------------------
//
//// w_500,e_contrast:50
//cloudinary.url {
//publicId("sample")
//adjust(Adjust.contrast(50))
//resize(Resize.scale(500))
//}.generate()
//
//// -----------------------
//
//// w_500,e_gamma:-20
//cloudinary.url {
//publicId("sample")
//adjust(Adjust.gamma(-20))
//resize(Resize.scale(500))
//}.generate()
//
//// -----------------------
//
//// w_500,e_reverse
//cloudinary.url {
//publicId("sample")
//effect(Effect.reverse())
//resize(Resize.scale(500))
//}.generate()
//
//// -----------------------
//
//// w_500,e_saturation:-50
//cloudinary.url {
//publicId("sample")
//adjust(Adjust.saturation(-50))
//resize(Resize.scale(500))
//}.generate()
//
//// -----------------------
//
//// w_500,e_vignette:50
//cloudinary.url {
//publicId("sample")
//effect(Effect.vignette(50))
//resize(Resize.scale(500))
//}.generate()
//
//// -----------------------
//
//// w_500,e_volume:50
//cloudinary.url {
//publicId("sample")
//effect(Effect.volume(50))
//resize(Resize.scale(500))
//}.generate()
//
//// -----------------------
//
//// w_500,f_auto
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//format(Format.auto())
//}.generate()
//
//// -----------------------
//
//// w_500,f_auto,q_auto,dpr_2/bo_1px_solid_gray
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//quality(Quality.auto())
//dpr(2)
//border(Border.solid() {
//width(1)
//color(Color.GRAY)
//})
//}.generate()
//
//// -----------------------
//
//// w_500,q_auto,f_auto
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//format(Format.auto())
//quality(Quality.auto())
//}.generate()
//
//// -----------------------
//
//// w_500,q_auto,f_auto,dpr_2
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//format(Format.auto())
//quality(Quality.auto())
//dpr(2)
//}.generate()
//
//// -----------------------
//
//// w_500/e_brightness:70/l_woman,c_crop,g_face/w_150/e_saturation:50/e_shadow/fl_layer_apply
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//adjust(Adjust.brightness(70))
//overlay(Layer.image("woman") {
//resize(Resize.crop() {
//gravity(Gravity.face())
//})
//resize(Resize.scale(150))
//adjust(Adjust.saturation(50))
//effect(Effect.shadow())
//})
//}.generate()
//
//// -----------------------
//
//// w_500/if_!cloudinary!_in_tags/l_cloudinary_icon,w_100,g_north_east,o_50,e_brightness:100/l_text:arial_15:By Cloudinary,g_north_east,y_10,x_105/if_end/du_5
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//ifCondition("!cloudinary! in tags")
//overlay(Layer.image("cloudinary_icon") {
//adjust(Adjust.brightness(100))
//adjust(Adjust.opacity(50))
//resize(Resize.scale(100) {
//gravity(Gravity.northEast())
//})
//})
//overlay(Layer.text("By Cloudinary") {
//fontFamily("arial")
//fontSize(15)
//}, Position.northEast(105, 10))
//endIfCondition()
//trim(Trim.duration(5))
//}.generate()
//
//// -----------------------
//
//// w_500/l_cloudinary_icon,g_north_east,o_50,w_200,e_anti_removal:90
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.image("cloudinary_icon") {
//adjust(Adjust.opacity(50))
//resize(Resize.scale(200) {
//gravity(Gravity.northEast())
//})
//}, , BlendMode.antiRemoval(90))
//}.generate()
//
//// -----------------------
//
//// w_500/l_cloudinary_icon,o_50,w_100,e_brightness:200,fl_tiled
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.image("cloudinary_icon") {
//tiled(.())
//adjust(Adjust.brightness(200))
//adjust(Adjust.opacity(50))
//resize(Resize.scale(100))
//})
//}.generate()
//
//// -----------------------
//
//// w_500/l_cloudinary_icon,w_150/e_blackwhite/fl_layer_apply,g_north_east
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.image("cloudinary_icon") {
//resize(Resize.scale(150))
//effect(Effect.blackwhite())
//}, Position.northEast())
//}.generate()
//
//// -----------------------
//
//// w_500/l_cloudinary_icon,w_150/e_blackwhite/l_cloudinary_icon,w_50/fl_layer_apply/fl_layer_apply,g_north_east
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.image("cloudinary_icon") {
//resize(Resize.scale(150))
//effect(Effect.blackwhite())
//overlay(Layer.image("cloudinary_icon") {
//resize(Resize.scale(50))
//})
//}, Position.northEast())
//}.generate()
//
//// -----------------------
//
//// w_500/l_text:Arial_80:Flowers
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.text("Flowers") {
//fontFamily("Arial")
//fontSize(80)
//})
//}.generate()
//
//// -----------------------
//
//// w_500/l_text:Times_60:Cool image,g_south,y_20
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.text("Cool image") {
//fontFamily("Times")
//fontSize(60)
//}, Position.south() {
//y(20)
//})
//}.generate()
//
//// -----------------------
//
//// w_500/l_text:Times_90_bold:Cool image,g_south,y_80,co_rgb:FFFF00
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.text("Cool image") {
//fontFamily("Times")
//fontSize(90)
//fontWeight(FontWeight.BOLD)
//color(Color.rgb("#FFFF00"))
//}, Position.south() {
//y(80)
//})
//}.generate()
//
//// -----------------------
//
//// w_500/l_text:Times_90_bold:Cool image,g_south,y_80,co_rgb:FFFF0080
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.text("Cool image") {
//fontFamily("Times")
//fontSize(90)
//fontWeight(FontWeight.BOLD)
//color(Color.rgb("#FFFF0080"))
//}, Position.south() {
//y(80)
//})
//}.generate()
//
//// -----------------------
//
//// w_500/l_text:Verdana_50_bold:Cool
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.text("Cool") {
//fontFamily("Verdana")
//fontSize(50)
//fontWeight(FontWeight.BOLD)
//})
//}.generate()
//
//// -----------------------
//
//// w_500/l_text:Verdana_75_bold_underline_letter_spacing_14:Flowers
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.text("Flowers") {
//fontFamily("Verdana")
//fontSize(75)
//fontWeight(FontWeight.BOLD)
//textDecoration(TextDecoration.UNDERLINE)
//letterSpacing(14)
//})
//}.generate()
//
//// -----------------------
//
//// w_500/l_text:sample_text_style:Stylish text,g_south
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.text("Stylish text") {
//fontFamily("sample")
//fontSize("text")
//}, Position.south())
//}.generate()
//
//// -----------------------
//
//// w_500/l_video:docs:bluescreen_watches,e_make_transparent:10,co_rgb:3d8d48,w_1.0,fl_relative/du_15
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.video("docs/bluescreen_watches") {
//effect(Effect.makeTransparent(10))
//color(Color.rgb("#3d8d48"))
//resize(Resize.scale(1.0))
//addFlag(Flag.relative())
//})
//trim(Trim.duration(15))
//}.generate()
//
//// -----------------------
//
//// w_500/l_video:docs:bluescreen_watches,e_make_transparent:20,co_rgb:0e80d8,w_0.6,fl_relative,g_north/du_15
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.video("docs/bluescreen_watches") {
//effect(Effect.makeTransparent(20))
//color(Color.rgb("#0e80d8"))
//resize(Resize.scale(0.6) {
//gravity(Gravity.north())
//})
//addFlag(Flag.relative())
//})
//trim(Trim.duration(15))
//}.generate()
//
//// -----------------------
//
//// w_500/w_400,c_fit,l_text:Neucha_26_bold:Lorem ipsum dolor sit amet consectetur adipisicing elit sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.text("Lorem ipsum dolor sit amet consectetur adipisicing elit sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.") {
//fontFamily("Neucha")
//fontSize(26)
//fontWeight(FontWeight.BOLD)
//resize(Resize.fit(400))
//})
//}.generate()
//
//// -----------------------
//
//// w_550,h_35,c_crop,g_north_west/bo_1px_solid_gray/dpr_auto
//cloudinary.url {
//publicId("sample")
//resize(Resize.crop(550, 35) {
//gravity(Gravity.northWest())
//})
//border(Border.solid() {
//width(1)
//color(Color.GRAY)
//})
//dpr(Dpr.auto())
//}.generate()
//
//// -----------------------
//
//// w_600
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(600))
//}.generate()
//
//// -----------------------
//
//// w_600,ar_1,c_thumb,g_auto:microwave
//cloudinary.url {
//publicId("sample")
//resize(Resize.thumbnail(600) {
//aspectRatio(1)
//gravity(Gravity.auto(ObjectGravity.MICROWAVE))
//})
//}.generate()
//
//// -----------------------
//
//// w_600,ar_1,c_thumb,g_auto:microwave/h_150
//cloudinary.url {
//publicId("sample")
//resize(Resize.thumbnail(600) {
//aspectRatio(1)
//gravity(Gravity.auto(ObjectGravity.MICROWAVE))
//})
//resize(Resize.scale() {
//height(150)
//})
//}.generate()
//
//// -----------------------
//
//// w_600,ar_1,c_thumb,g_auto:refrigerator
//cloudinary.url {
//publicId("sample")
//resize(Resize.thumbnail(600) {
//aspectRatio(1)
//gravity(Gravity.auto(ObjectGravity.REFRIGERATOR))
//})
//}.generate()
//
//// -----------------------
//
//// w_600,ar_1,c_thumb,g_auto:refrigerator/h_150
//cloudinary.url {
//publicId("sample")
//resize(Resize.thumbnail(600) {
//aspectRatio(1)
//gravity(Gravity.auto(ObjectGravity.REFRIGERATOR))
//})
//resize(Resize.scale() {
//height(150)
//})
//}.generate()
//
//// -----------------------
//
//// w_600,ar_1,c_thumb,g_auto:sink
//cloudinary.url {
//publicId("sample")
//resize(Resize.thumbnail(600) {
//aspectRatio(1)
//gravity(Gravity.auto(ObjectGravity.SINK))
//})
//}.generate()
//
//// -----------------------
//
//// w_600,ar_1,c_thumb,g_auto:sink/h_150
//cloudinary.url {
//publicId("sample")
//resize(Resize.thumbnail(600) {
//aspectRatio(1)
//gravity(Gravity.auto(ObjectGravity.SINK))
//})
//resize(Resize.scale() {
//height(150)
//})
//}.generate()
//
//// -----------------------
//
//// w_600/w_570,h_43,c_crop,g_north_west/bo_1px_solid_gray
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(600))
//resize(Resize.crop(570, 43) {
//gravity(Gravity.northWest())
//})
//border(Border.solid() {
//width(1)
//color(Color.GRAY)
//})
//}.generate()
//
//// -----------------------
//
//// w_700,f_auto,q_auto,dpr_2/bo_1px_solid_gray
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(700))
//quality(Quality.auto())
//dpr(2)
//border(Border.solid() {
//width(1)
//color(Color.GRAY)
//})
//}.generate()
//
//// -----------------------
//
//// w_700,h_700,c_fill
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(700, 700))
//}.generate()
//
//// -----------------------
//
//// w_700,h_700,c_fill/e_style_transfer,l_sailing_angel
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(700, 700))
//effect(Effect.styleTransfer() {
//overlay(Layer.image("sailing_angel"))
//})
//}.generate()
//
//// -----------------------
//
//// w_700,h_700,c_fill/e_style_transfer:60,l_lighthouse/w_250
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(700, 700))
//effect(Effect.styleTransfer() {
//overlay(Layer.image("lighthouse"))
//strength(60)
//})
//resize(Resize.scale(250))
//}.generate()
//
//// -----------------------
//
//// w_700,h_700,c_fill/e_style_transfer:preserve_color,l_lighthouse/w_250
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(700, 700))
//effect(Effect.styleTransfer() {
//overlay(Layer.image("lighthouse"))
//strength("preserve_color")
//})
//resize(Resize.scale(250))
//}.generate()
//
//// -----------------------
//
//// w_700,h_700,c_fill/w_250
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(700, 700))
//resize(Resize.scale(250))
//}.generate()
//
//// -----------------------
//
//// w_700,r_max/fl_region_relative,g_adv_eyes,l_harlequinmask,w_1.7
//cloudinary.url {
//publicId("sample")
//roundCorners(RoundCorners.max())
//resize(Resize.scale(700))
//overlay(Layer.image("harlequinmask") {
//position(Position.advancedEyes())
//resize(Resize.scale(1.7) {
//resizeMode(ResizeMode.regionRelative())
//})
//})
//}.generate()
//
//// -----------------------
//
//// w_700/dpr_auto/bo_1px_solid_gray
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(700))
//dpr(Dpr.auto())
//border(Border.solid() {
//width(1)
//color(Color.GRAY)
//})
//}.generate()
//
//// -----------------------
//
//// w_750,h_750,c_fill,g_faces
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(750, 750) {
//gravity(Gravity.faces())
//})
//}.generate()
//
//// -----------------------
//
//// w_80,h_120,c_fill,g_face
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(80, 120) {
//gravity(Gravity.face())
//})
//}.generate()
//
//// -----------------------
//
//// w_80,h_400,c_fill,g_auto
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(80, 400) {
//gravity(Gravity.auto())
//})
//}.generate()
//
//// -----------------------
//
//// w_80,h_400,c_fill_pad,g_auto,b_auto
//cloudinary.url {
//publicId("sample")
//resize(Resize.fillPad(80, 400) {
//gravity(Gravity.auto())
//})
//background(Color.AUTO)
//}.generate()
//
//// -----------------------
//
//// w_800
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(800))
//}.generate()
//
//// -----------------------
//
//// w_800/bo_1px_solid_gray
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(800))
//border(Border.solid() {
//width(1)
//color(Color.GRAY)
//})
//}.generate()
//
//// -----------------------
//
//// w_850,dpr_2
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(850))
//dpr(2)
//}.generate()
//
//// -----------------------
//
//// w_abc
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale("abc"))
//}.generate()
//
//// -----------------------
//
//// w_auto
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale("auto"))
//}.generate()
//
//// -----------------------
//
//// w_auto,c_scale
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale("auto"))
//}.generate()
//
//// -----------------------
//
//// w_auto,dpr_auto,c_limit/g_north_east,y_5,x_5,co_white,l_text:Arial_18_bold:dpr_auto
//cloudinary.url {
//publicId("sample")
//resize(Resize.limitFit("auto"))
//dpr(Dpr.auto())
//overlay(Layer.text("dpr_auto") {
//fontFamily("Arial")
//fontSize(18)
//fontWeight(FontWeight.BOLD)
//color(Color.WHITE)
//}, Position.northEast(5, 5))
//}.generate()
//
//// -----------------------
//
//// w_auto,f_auto,c_scale
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale("auto"))
//format(Format.auto())
//}.generate()
//
//// -----------------------
//
//// w_auto,g_auto,c_fill
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill("auto") {
//gravity(Gravity.auto())
//})
//}.generate()
//
//// -----------------------
//
//// w_auto,q_auto,c_scale
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale("auto"))
//quality(Quality.auto())
//}.generate()
//
//// -----------------------
//
//// x_355,y_410,w_300,h_200,c_crop
//cloudinary.url {
//publicId("sample")
//resize(Resize.crop(300, 200) {
//gravity(Gravity.absolute(355, 410))
//})
//}.generate()
//
//// -----------------------
//
//// x_355,y_410,w_300,h_200,c_crop/w_130,h_100,c_fill
//cloudinary.url {
//publicId("sample")
//resize(Resize.crop(300, 200) {
//gravity(Gravity.absolute(355, 410))
//})
//resize(Resize.fill(130, 100))
//}.generate()
//
//// -----------------------
//
//// x_355,y_410,w_300,h_200,c_crop/w_130,h_100,c_fill/a_20/w_0.5
//cloudinary.url {
//publicId("sample")
//resize(Resize.crop(300, 200) {
//gravity(Gravity.absolute(355, 410))
//})
//resize(Resize.fill(130, 100))
//rotate(Rotate.angle(20))
//resize(Resize.scale(0.5))
//}.generate()
//
//// -----------------------
//
//// x_355,y_410,w_300,h_200,c_crop/w_150,h_100,c_scale
//cloudinary.url {
//publicId("sample")
//resize(Resize.crop(300, 200) {
//gravity(Gravity.absolute(355, 410))
//})
//resize(Resize.scale(150, 100))
//}.generate()
//
//// -----------------------
//
//// w_100,c_fill
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(100))
//}.generate()
//
//// -----------------------
//
//// f_jpg,u_some
//cloudinary.url {
//publicId("sample")
//underlay(Layer.image("some") {
//format(Format.jpg())
//})
//}.generate()
//
//// -----------------------
//
//// f_jpg/u_some
//cloudinary.url {
//publicId("sample")
//format(Format.jpg())
//underlay(Layer.image("some"))
//}.generate()
//
//// -----------------------
//
//// e_pixelate_region,w_100
//cloudinary.url {
//publicId("sample")
//effect(Effect.pixelateRegion() {
//width(100)
//})
//}.generate()
//
//// -----------------------
//
//// c_fill,w_100_mul_2
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill("100 * 2"))
//}.generate()
//
//// -----------------------
//
//// c_fill,w_100_mul_2
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill("100 * 2"))
//}.generate()
//
//// -----------------------
//
//// c_fill,w_100_mul_2
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill("100 * 2"))
//}.generate()
//
//// -----------------------
//
//// w_500/e_brightness:70/l_woman,c_crop,g_face/w_150/e_saturation:50/e_shadow/fl_layer_apply
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//adjust(Adjust.brightness(70))
//overlay(Layer.image("woman") {
//resize(Resize.crop() {
//gravity(Gravity.face())
//})
//resize(Resize.scale(150))
//adjust(Adjust.saturation(50))
//effect(Effect.shadow())
//})
//}.generate()
//
//// -----------------------
//
//// w_500,ar_4:3
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500) {
//aspectRatio("4:3")
//})
//}.generate()
//
//// -----------------------
//
//// w_500,ar_4:3
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500) {
//aspectRatio("4:3")
//})
//}.generate()
//
//// -----------------------
//
//// w_500,ar_4:3
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500) {
//aspectRatio("4:3")
//})
//}.generate()
//
//// -----------------------
//
//// c_fill,ar_4:3
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill() {
//aspectRatio("4:3")
//})
//}.generate()
//
//// -----------------------
//
//// ar_4:3,c_fill/c_scale,w_auto,dpr_auto
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill() {
//aspectRatio("4:3")
//})
//resize(Resize.scale("auto"))
//dpr(Dpr.auto())
//}.generate()
//
//// -----------------------
//
//// ar_4:3,c_fill/c_scale,w_auto,dpr_auto
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill() {
//aspectRatio("4:3")
//})
//resize(Resize.scale("auto"))
//dpr(Dpr.auto())
//}.generate()
//
//// -----------------------
//
//// ar_4:3,c_fill/c_scale,w_auto,dpr_auto
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill() {
//aspectRatio("4:3")
//})
//resize(Resize.scale("auto"))
//dpr(Dpr.auto())
//}.generate()
//
//// -----------------------
//
//// w_500/l_my_icon
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.image("my_icon"))
//}.generate()
//
//// -----------------------
//
//// w_500/l_text:verdana_1:words
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.text("words") {
//fontFamily("verdana")
//fontSize(1)
//})
//}.generate()
//
//// -----------------------
//
//// w_500/l_text:verdana_12_underline_bold_left:words
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.text("words") {
//fontFamily("verdana")
//fontSize(12)
//fontWeight(FontWeight.BOLD)
//textDecoration(TextDecoration.UNDERLINE)
//textAlignment(TextAlignment.LEFT)
//})
//}.generate()
//
//// -----------------------
//
//// w_500/l_text:Verdana_75_bold_underline_stroke_letter_spacing_14_line_spacing_4:words,y_24
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.text("words") {
//fontFamily("Verdana")
//fontSize(75)
//fontWeight(FontWeight.BOLD)
//textDecoration(TextDecoration.UNDERLINE)
//letterSpacing(14)
//stroke(Stroke.STROKE)
//lineSpacing(4)
//}, Position.absolute() {
//y(24)
//})
//}.generate()
//
//// -----------------------
//
//// w_500/l_text:sample_text_style:Stylish%20text,g_south
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.text("Stylish%20text") {
//fontFamily("sample")
//fontSize("text")
//}, Position.south())
//}.generate()
//
//// -----------------------
//
//// w_500/u_my_bg
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//underlay(Layer.image("my_bg"))
//}.generate()
//
//// -----------------------
//
//// w_500/u_fetch:aW1hZ2UudXJs
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//underlay(Layer.image("fetch/aW1hZ2UudXJs"))
//}.generate()
//
//// -----------------------
//
//// w_500/l_subtitles:Arial_40:sample_sub_he.srt
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.subtitles("sample_sub_he.srt") {
//fontFamily("Arial")
//fontSize(40)
//})
//}.generate()
//
//// -----------------------
//
//// l_subtitles:impact_15:lincoln.transcript,co_khaki,b_rgb:331a00,g_south_west
//cloudinary.url {
//publicId("sample")
//overlay(Layer.subtitles("lincoln.transcript") {
//fontFamily("impact")
//fontSize(15)
//color(Color.KHAKI)
//background(Color.rgb("#331a00"))
//}, Position.southWest())
//}.generate()
//
//// -----------------------
//
//// w_500/l_text:verdana_12_underline_bold_left:words
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.text("words") {
//fontFamily("verdana")
//fontSize(12)
//fontWeight(FontWeight.BOLD)
//textDecoration(TextDecoration.UNDERLINE)
//textAlignment(TextAlignment.LEFT)
//})
//}.generate()
//
//// -----------------------
//
//// w_500/l_text:verdana_12_underline_bold_left:words,y_23
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.text("words") {
//fontFamily("verdana")
//fontSize(12)
//fontWeight(FontWeight.BOLD)
//textDecoration(TextDecoration.UNDERLINE)
//textAlignment(TextAlignment.LEFT)
//}, Position.absolute() {
//y(23)
//})
//}.generate()
//
//// -----------------------
//
//// w_500/u_fetch:aW1hZ2UudXJs
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//underlay(Layer.image("fetch/aW1hZ2UudXJs"))
//}.generate()
//
//// -----------------------
//
//// w_500/l_subtitles:Arial_40:sample_sub_he.srt
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.subtitles("sample_sub_he.srt") {
//fontFamily("Arial")
//fontSize(40)
//})
//}.generate()
//
//// -----------------------
//
//// l_subtitles:impact_15:lincoln.transcript,co_khaki,b_rgb:331a00,g_south_west
//cloudinary.url {
//publicId("sample")
//overlay(Layer.subtitles("lincoln.transcript") {
//fontFamily("impact")
//fontSize(15)
//color(Color.KHAKI)
//background(Color.rgb("#331a00"))
//}, Position.southWest())
//}.generate()
//
//// -----------------------
//
//// w_500/l_text:sample_text_style:Stylish%20text,g_south
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.text("Stylish%20text") {
//fontFamily("sample")
//fontSize("text")
//}, Position.south())
//}.generate()
//
//// -----------------------
//
//// w_500/l_text:verdana_12_underline_bold_left:words,y_23
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.text("words") {
//fontFamily("verdana")
//fontSize(12)
//fontWeight(FontWeight.BOLD)
//textDecoration(TextDecoration.UNDERLINE)
//textAlignment(TextAlignment.LEFT)
//}, Position.absolute() {
//y(23)
//})
//}.generate()
//
//// -----------------------
//
//// w_500/u_fetch:aW1hZ2UudXJs
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//underlay(Layer.image("fetch/aW1hZ2UudXJs"))
//}.generate()
//
//// -----------------------
//
//// w_500/l_subtitles:Arial_40:sample_sub_he.srt
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.subtitles("sample_sub_he.srt") {
//fontFamily("Arial")
//fontSize(40)
//})
//}.generate()
//
//// -----------------------
//
//// l_subtitles:impact_15:lincoln.transcript,co_khaki,b_rgb:331a00,g_south_west
//cloudinary.url {
//publicId("sample")
//overlay(Layer.subtitles("lincoln.transcript") {
//fontFamily("impact")
//fontSize(15)
//color(Color.KHAKI)
//background(Color.rgb("#331a00"))
//}, Position.southWest())
//}.generate()
//
//// -----------------------
//
//// w_500/l_text:sample_text_style:Stylish%20text,g_south
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.text("Stylish%20text") {
//fontFamily("sample")
//fontSize("text")
//}, Position.south())
//}.generate()
//
//// -----------------------
//
//// w_500/l_text:Verdana_75_bold_underline_letter_spacing_14_stroke_line_spacing_4:words,y_24
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.text("words") {
//fontFamily("Verdana")
//fontSize(75)
//fontWeight(FontWeight.BOLD)
//textDecoration(TextDecoration.UNDERLINE)
//letterSpacing(14)
//stroke(Stroke.STROKE)
//lineSpacing(4)
//}, Position.absolute() {
//y(24)
//})
//}.generate()
//
//// -----------------------
//
//// w_500/l_text:sample_text_style:Stylish%20text,g_south
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.text("Stylish%20text") {
//fontFamily("sample")
//fontSize("text")
//}, Position.south())
//}.generate()
//
//// -----------------------
//
//// w_500/u_fetch:aW1hZ2UudXJs
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//underlay(Layer.image("fetch/aW1hZ2UudXJs"))
//}.generate()
//
//// -----------------------
//
//// w_500/l_subtitles:Arial_40:sample_sub_he.srt
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.subtitles("sample_sub_he.srt") {
//fontFamily("Arial")
//fontSize(40)
//})
//}.generate()
//
//// -----------------------
//
//// l_subtitles:impact_15:lincoln.transcript,co_khaki,b_rgb:331a00,g_south_west
//cloudinary.url {
//publicId("sample")
//overlay(Layer.subtitles("lincoln.transcript") {
//fontFamily("impact")
//fontSize(15)
//color(Color.KHAKI)
//background(Color.rgb("#331a00"))
//}, Position.southWest())
//}.generate()
//
//// -----------------------
//
//// w_500/l_text:Verdana_75_bold_underline_letter_spacing_14_stroke_line_spacing_4:words,y_24
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.text("words") {
//fontFamily("Verdana")
//fontSize(75)
//fontWeight(FontWeight.BOLD)
//textDecoration(TextDecoration.UNDERLINE)
//letterSpacing(14)
//stroke(Stroke.STROKE)
//lineSpacing(4)
//}, Position.absolute() {
//y(24)
//})
//}.generate()
//
//// -----------------------
//
//// w_500/u_fetch:aW1hZ2UudXJs
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//underlay(Layer.image("fetch/aW1hZ2UudXJs"))
//}.generate()
//
//// -----------------------
//
//// w_500/l_subtitles:Arial_40:sample_sub_he.srt
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.subtitles("sample_sub_he.srt") {
//fontFamily("Arial")
//fontSize(40)
//})
//}.generate()
//
//// -----------------------
//
//// l_subtitles:impact_15:lincoln.transcript,co_khaki,b_rgb:331a00,g_south_west
//cloudinary.url {
//publicId("sample")
//overlay(Layer.subtitles("lincoln.transcript") {
//fontFamily("impact")
//fontSize(15)
//color(Color.KHAKI)
//background(Color.rgb("#331a00"))
//}, Position.southWest())
//}.generate()
//
//// -----------------------
//
//// w_500/u_fetch:aW1hZ2UudXJs
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//underlay(Layer.image("fetch/aW1hZ2UudXJs"))
//}.generate()
//
//// -----------------------
//
//// w_500/l_text:Verdana_75_bold_underline_letter_spacing_14_stroke_line_spacing_4:words,y_24
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.text("words") {
//fontFamily("Verdana")
//fontSize(75)
//fontWeight(FontWeight.BOLD)
//textDecoration(TextDecoration.UNDERLINE)
//letterSpacing(14)
//stroke(Stroke.STROKE)
//lineSpacing(4)
//}, Position.absolute() {
//y(24)
//})
//}.generate()
//
//// -----------------------
//
//// w_500/l_subtitles:Arial_40:sample_sub_he.srt
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.subtitles("sample_sub_he.srt") {
//fontFamily("Arial")
//fontSize(40)
//})
//}.generate()
//
//// -----------------------
//
//// if_w_lt_600/l_text:Arial_20:Image%20shown%20in%20full%20scale,co_white,g_south_east/c_scale,e_blur:400,u_small_dinosaur,w_600/if_end
//cloudinary.url {
//publicId("sample")
//ifCondition("w < 600")
//overlay(Layer.text("Image%20shown%20in%20full%20scale") {
//fontFamily("Arial")
//fontSize(20)
//color(Color.WHITE)
//}, Position.southEast())
//underlay(Layer.image("small_dinosaur") {
//effect(Effect.blur(400))
//resize(Resize.scale(600))
//})
//endIfCondition()
//}.generate()
//
//// -----------------------
//
//// l_subtitles:impact_15:lincoln.transcript,co_khaki,b_rgb:331a00,g_south_west
//cloudinary.url {
//publicId("sample")
//overlay(Layer.subtitles("lincoln.transcript") {
//fontFamily("impact")
//fontSize(15)
//color(Color.KHAKI)
//background(Color.rgb("#331a00"))
//}, Position.southWest())
//}.generate()
//
//// -----------------------
//
//// w_500/l_text:Verdana_75_bold_underline_letter_spacing_14_stroke_line_spacing_4:words,y_24
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.text("words") {
//fontFamily("Verdana")
//fontSize(75)
//fontWeight(FontWeight.BOLD)
//textDecoration(TextDecoration.UNDERLINE)
//letterSpacing(14)
//stroke(Stroke.STROKE)
//lineSpacing(4)
//}, Position.absolute() {
//y(24)
//})
//}.generate()
//
//// -----------------------
//
//// w_500/u_fetch:aW1hZ2UudXJs
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//underlay(Layer.image("fetch/aW1hZ2UudXJs"))
//}.generate()
//
//// -----------------------
//
//// c_fill,h_200,w_200/e_brightness:100,h_200,u_site_bg,w_200
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(200, 200))
//underlay(Layer.image("site_bg") {
//adjust(Adjust.brightness(100))
//resize(Resize.scale(200, 200))
//})
//}.generate()
//
//// -----------------------
//
//// w_500/l_subtitles:Arial_40:sample_sub_he.srt
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.subtitles("sample_sub_he.srt") {
//fontFamily("Arial")
//fontSize(40)
//})
//}.generate()
//
//// -----------------------
//
//// l_subtitles:impact_15:lincoln.transcript,co_khaki,b_rgb:331a00,g_south_west
//cloudinary.url {
//publicId("sample")
//overlay(Layer.subtitles("lincoln.transcript") {
//fontFamily("impact")
//fontSize(15)
//color(Color.KHAKI)
//background(Color.rgb("#331a00"))
//}, Position.southWest())
//}.generate()
//
//// -----------------------
//
//// w_500/l_text:Verdana_75_bold_underline_letter_spacing_14_stroke_line_spacing_4:words,y_24
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.text("words") {
//fontFamily("Verdana")
//fontSize(75)
//fontWeight(FontWeight.BOLD)
//textDecoration(TextDecoration.UNDERLINE)
//letterSpacing(14)
//stroke(Stroke.STROKE)
//lineSpacing(4)
//}, Position.absolute() {
//y(24)
//})
//}.generate()
//
//// -----------------------
//
//// w_500/u_fetch:aW1hZ2UudXJs
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//underlay(Layer.image("fetch/aW1hZ2UudXJs"))
//}.generate()
//
//// -----------------------
//
//// w_500/l_subtitles:Arial_40:sample_sub_he.srt
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.subtitles("sample_sub_he.srt") {
//fontFamily("Arial")
//fontSize(40)
//})
//}.generate()
//
//// -----------------------
//
//// l_subtitles:impact_15:lincoln.transcript,co_khaki,b_rgb:331a00,g_south_west
//cloudinary.url {
//publicId("sample")
//overlay(Layer.subtitles("lincoln.transcript") {
//fontFamily("impact")
//fontSize(15)
//color(Color.KHAKI)
//background(Color.rgb("#331a00"))
//}, Position.southWest())
//}.generate()
//
//// -----------------------
//
//// w_500/l_my_bg
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.image("my_bg"))
//}.generate()
//
//// -----------------------
//
//// w_500/l_text:Verdana_75_bold_underline_letter_spacing_14_stroke_line_spacing_4:words,y_24
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.text("words") {
//fontFamily("Verdana")
//fontSize(75)
//fontWeight(FontWeight.BOLD)
//textDecoration(TextDecoration.UNDERLINE)
//letterSpacing(14)
//stroke(Stroke.STROKE)
//lineSpacing(4)
//}, Position.absolute() {
//y(24)
//})
//}.generate()
//
//// -----------------------
//
//// w_500/l_text:sample_text_style:Stylish%20text,g_south
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.text("Stylish%20text") {
//fontFamily("sample")
//fontSize("text")
//}, Position.south())
//}.generate()
//
//// -----------------------
//
//// w_500/u_fetch:aW1hZ2UudXJs
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//underlay(Layer.image("fetch/aW1hZ2UudXJs"))
//}.generate()
//
//// -----------------------
//
//// w_500/l_subtitles:Arial_40:sample_sub_he.srt
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.subtitles("sample_sub_he.srt") {
//fontFamily("Arial")
//fontSize(40)
//})
//}.generate()
//
//// -----------------------
//
//// w_500/l_subtitles:sample_sub_he.srt
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.subtitles("") {
//fontFamily("sample")
//fontSize("sub")
//})
//}.generate()
//
//// -----------------------
//
//// l_subtitles:impact_15:lincoln.transcript,co_khaki,b_rgb:331a00,g_south_west
//cloudinary.url {
//publicId("sample")
//overlay(Layer.subtitles("lincoln.transcript") {
//fontFamily("impact")
//fontSize(15)
//color(Color.KHAKI)
//background(Color.rgb("#331a00"))
//}, Position.southWest())
//}.generate()
//
//// -----------------------
//
//// w_500/l_my_bg
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.image("my_bg"))
//}.generate()
//
//// -----------------------
//
//// w_500/l_text:Verdana_75_bold_underline_letter_spacing_14_stroke_line_spacing_4:words,y_24
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.text("words") {
//fontFamily("Verdana")
//fontSize(75)
//fontWeight(FontWeight.BOLD)
//textDecoration(TextDecoration.UNDERLINE)
//letterSpacing(14)
//stroke(Stroke.STROKE)
//lineSpacing(4)
//}, Position.absolute() {
//y(24)
//})
//}.generate()
//
//// -----------------------
//
//// w_500/u_fetch:aW1hZ2UudXJs
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//underlay(Layer.image("fetch/aW1hZ2UudXJs"))
//}.generate()
//
//// -----------------------
//
//// w_500/l_subtitles:Arial_40:sample_sub_he.srt
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.subtitles("sample_sub_he.srt") {
//fontFamily("Arial")
//fontSize(40)
//})
//}.generate()
//
//// -----------------------
//
//// l_subtitles:impact_15:lincoln.transcript,co_khaki,b_rgb:331a00,g_south_west
//cloudinary.url {
//publicId("sample")
//overlay(Layer.subtitles("lincoln.transcript") {
//fontFamily("impact")
//fontSize(15)
//color(Color.KHAKI)
//background(Color.rgb("#331a00"))
//}, Position.southWest())
//}.generate()
//
//// -----------------------
//
//// w_500/l_my_bg
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.image("my_bg"))
//}.generate()
//
//// -----------------------
//
//// w_500/l_text:Verdana_75_bold_underline_letter_spacing_14_stroke_line_spacing_4:words,y_24
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.text("words") {
//fontFamily("Verdana")
//fontSize(75)
//fontWeight(FontWeight.BOLD)
//textDecoration(TextDecoration.UNDERLINE)
//letterSpacing(14)
//stroke(Stroke.STROKE)
//lineSpacing(4)
//}, Position.absolute() {
//y(24)
//})
//}.generate()
//
//// -----------------------
//
//// w_500/l_text:sample_text_style:Stylish%20text,g_south
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.text("Stylish%20text") {
//fontFamily("sample")
//fontSize("text")
//}, Position.south())
//}.generate()
//
//// -----------------------
//
//// w_500/u_fetch:aW1hZ2UudXJs
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//underlay(Layer.image("fetch/aW1hZ2UudXJs"))
//}.generate()
//
//// -----------------------
//
//// w_500/l_subtitles:Arial_40:sample_sub_he.srt
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(500))
//overlay(Layer.subtitles("sample_sub_he.srt") {
//fontFamily("Arial")
//fontSize(40)
//})
//}.generate()
//
//// -----------------------
//
//// l_subtitles:impact_15:lincoln.transcript,co_khaki,b_rgb:331a00,g_south_west
//cloudinary.url {
//publicId("sample")
//overlay(Layer.subtitles("lincoln.transcript") {
//fontFamily("impact")
//fontSize(15)
//color(Color.KHAKI)
//background(Color.rgb("#331a00"))
//}, Position.southWest())
//}.generate()
//
//// -----------------------
//
//// c_fill,g_face,h_10,w_10
//cloudinary.url {
//publicId("sample")
//resize(Resize.fill(10, 10) {
//gravity(Gravity.face())
//})
//}.generate()
//
//// -----------------------
//
//// a_180
//cloudinary.url {
//publicId("sample")
//rotate(Rotate.angle(180))
//}.generate()
//
//// -----------------------
//
//// a_exif
//cloudinary.url {
//publicId("sample")
//rotate(Rotate.exif())
//}.generate()
//
//// -----------------------
//
//// e_hue
//cloudinary.url {
//publicId("sample")
//adjust(Adjust.hue())
//}.generate()
//
//// -----------------------
//
//// b_blue,o_0
//cloudinary.url {
//publicId("sample")
//adjust(Adjust.opacity(0))
//background(Color.BLUE)
//}.generate()
//
//// -----------------------
//
//// bo_5px_solid_rgb:aabbcc,r_max
//cloudinary.url {
//publicId("sample")
//roundCorners(RoundCorners.max())
//border(Border.solid() {
//width(5)
//color(Color.rgb("#aabbcc"))
//})
//}.generate()
//
//// -----------------------
//
//// bo_5.5px_solid_rgb:aabbcc,r_max
//cloudinary.url {
//publicId("sample")
//roundCorners(RoundCorners.max())
//border(Border.solid() {
//width(5.5)
//color(Color.rgb("#aabbcc"))
//})
//}.generate()
//
//// -----------------------
//
//// cs_no_cmyk
//cloudinary.url {
//publicId("sample")
//addGenericParam("color_space", "no_cmyk")
//}.generate()
//
//// -----------------------
//
//// fl_progressive,r_10
//cloudinary.url {
//publicId("sample")
//roundCorners(10)
//addFlag(Flag.progressive())
//}.generate()
//
//// -----------------------
//
//// t_sample_named_transform
//cloudinary.url {
//publicId("sample")
//namedTransformation("sample_named_transform")
//}.generate()
//
//// -----------------------
//
//// t_sample_named_transform.sample_named_transform2
//cloudinary.url {
//publicId("sample")
//namedTransformation("sample_named_transform.sample_named_transform2")
//}.generate()
//
//// -----------------------
//
//// t_sample_named_transform
//cloudinary.url {
//publicId("sample")
//namedTransformation("sample_named_transform")
//}.generate()
//
//// -----------------------
//
//// c_scale,t_sample_named_transform,w_1.0/t_sample_named_transform2
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(1.0))
//namedTransformation("sample_named_transform")
//namedTransformation("sample_named_transform2")
//}.generate()
//
//// -----------------------
//
//// c_scale,w_0.1/e_sepia:30
//cloudinary.url {
//publicId("sample")
//resize(Resize.scale(0.1))
//effect(Effect.sepia(30))
//}.generate()
//
//// -----------------------
//
//// $newwidth_1080/w_$newwidth/$name_!Jane%20Smith!/l_text:Comic%20Sans%20MS_24:%C2%A9$(name),g_south_east,x_10,y_10
//cloudinary.url {
//publicId("sample")
//variable("newwidth", 1080)
//resize(Resize.scale("\$newwidth"))
//variable("name", "Jane%20Smith")
//overlay(Layer.text("%C2%A9\$(name)") {
//fontFamily("Comic%20Sans%20MS")
//fontSize(24)
//}, Position.southEast(10, 10))
//}.generate()
//
//// -----------------------
//
//// $small_150,$big_2_mul_$small/c_fill,w_$big,h_$small_add_20
//cloudinary.url {
//publicId("sample")
//variable("small", 150)
//variable("big", "2 * \$small")
//resize(Resize.fill("\$big", "\$small + 20"))
//}.generate()
//
//// -----------------------
//
//// $small_150/c_fill,w_$big,h_$small_add_20
//cloudinary.url {
//publicId("sample")
//variable("small", 150)
//resize(Resize.fill("\$big", "\$small + 20"))
//}.generate()
//
//// -----------------------
//
//// $small_150,$big_2_mul_$small_add_4/c_fill,w_$big,h_$small_add_20
//cloudinary.url {
//publicId("sample")
//variable("small", 150)
//variable("big", "2 * \$small + 4")
//resize(Resize.fill("\$big", "\$small + 20"))
//}.generate()
//
//// -----------------------
//
//// $small_150,$big_2_mul_$small_add_4/c_fill,w_$big,h_$small_add_20
//cloudinary.url {
//publicId("sample")
//variable("small", 150)
//variable("big", "2 * \$small + 4")
//resize(Resize.fill("\$big", "\$small + 20"))
//}.generate()
//
//// -----------------------
//
//// $small_150,$big_2_mul_$small/c_fill,w_$big,h_$small_add_20/t_my_named_mul_trans
//cloudinary.url {
//publicId("sample")
//variable("small", 150)
//variable("big", "2 * \$small")
//resize(Resize.fill("\$big", "\$small + 20"))
//namedTransformation("my_named_mul_trans")
//}.generate()
//
//// -----------------------
//
//// $small_150,$big_2_div_$small/c_fill,w_$big,h_$small_add_20/l_my_named_div_layer/e_art:20
//cloudinary.url {
//publicId("sample")
//variable("small", 150)
//variable("big", "2 / \$small")
//resize(Resize.fill("\$big", "\$small + 20"))
//overlay(Layer.image("my_named_div_layer"))
//effect(Effect.artisticFilter(20))
//}.generate()
//
//// -----------------------
//
//// $small_150,$big_2_div_$small/c_fill,w_$big,h_$small_add_20/l_my_named_div_layer/e_art:20
//cloudinary.url {
//publicId("sample")
//variable("small", 150)
//variable("big", "2 / \$small")
//resize(Resize.fill("\$big", "\$small + 20"))
//overlay(Layer.image("my_named_div_layer"))
//effect(Effect.artisticFilter(20))
//}.generate()
//
//// -----------------------
//
//// $small_150,$big_2_div_$small/c_fill,w_$big,h_$small_add_20/l_my_named_div_layer/e_art:20
//cloudinary.url {
//publicId("sample")
//variable("small", 150)
//variable("big", "2 / \$small")
//resize(Resize.fill("\$big", "\$small + 20"))
//overlay(Layer.image("my_named_div_layer"))
//effect(Effect.artisticFilter(20))
//}.generate()
//
//// -----------------------
//
//// $small_150,$big_2_div_$small/c_fill,w_$big,h_$small_add_20/l_my_named_div_layer/e_art:20
//cloudinary.url {
//publicId("sample")
//variable("small", 150)
//variable("big", "2 / \$small")
//resize(Resize.fill("\$big", "\$small + 20"))
//overlay(Layer.image("my_named_div_layer"))
//effect(Effect.artisticFilter(20))
//}.generate()
//
//// -----------------------
//
//// $w_200,$ar_0.8/w_$w,ar_$ar,c_fill,g_face
//cloudinary.url {
//publicId("sample")
//variable("w", 200)
//variable("ar", 0.8)
//resize(Resize.fill("\$w") {
//aspectRatio("\$ar")
//gravity(Gravity.face())
//})
//}.generate()


    }
}

