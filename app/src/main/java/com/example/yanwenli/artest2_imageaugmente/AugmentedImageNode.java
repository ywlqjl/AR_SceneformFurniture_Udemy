package com.example.yanwenli.artest2_imageaugmente;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.ar.core.AugmentedImage;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.schemas.lull.Vec2;

import java.util.concurrent.CompletableFuture;

public class AugmentedImageNode extends AnchorNode implements Node.OnTapListener, Node.OnTouchListener{


    private static final String TAG = "AugmentedImageNode"; //节点标签
    private AugmentedImage image; //增强的图像

    private static CompletableFuture<ModelRenderable> ulCorner;
    private static CompletableFuture<ViewRenderable> test;
    private Node cornerNode;
    public Context nodeContext;


//    private static CompletableFuture<>

    @RequiresApi(api = Build.VERSION_CODES.N)
    public AugmentedImageNode(Context context){
        this.nodeContext = context;
        if (test == null) {
//            ulCorner =
//                    ModelRenderable.builder()
//                            .setSource(context, Uri.parse("models/frame_upper_left.sfb"))
//                            .build();
//            test =
//                    ModelRenderable.builder()
//                            .setSource(context, Uri.parse("assets/ImageTestAR.png"))
//                            .build();
            test = ViewRenderable.builder()
                    .setView(context, R.layout.test_layout)
                    .build();

            }

//        setOnTapListener(this);

    }

    public AugmentedImage getImage() {

        return image;
    }

    public void setImage(AugmentedImage image) {
        this.image = image;

        // If any of the models are not loaded, then recurse when all are loaded.
//        if (!ulCorner.isDone() || !urCorner.isDone() || !lrCorner.isDone()|| !test.isDone()) {
//            CompletableFuture.allOf(ulCorner, urCorner, lrCorner, test)
//                    .thenAccept((Void aVoid) -> setImage(image))
//                    .exceptionally(
//                            throwable -> {
//                                Log.e(TAG, "Exception loading", throwable);
//                                return null;
//                            });
//        }
        if(!test.isDone()){
            CompletableFuture.allOf(test)
                    .thenAccept((Void aVoid) -> setImage(image))
                    .exceptionally(
                            throwable -> {
                                Log.e(TAG, "Exception loading", throwable);
                                return null;
                            });
        }

        // Set the anchor based on the center of the image.
        setAnchor(image.createAnchor(image.getCenterPose()));

        // Make the 4 corner nodes.
        Vector3 localPosition = new Vector3();

        Vec2 vec2 = new Vec2();


        // Upper left corner.
//        localPosition.set(-0.5f * image.getExtentX(), 0.0f, -0.5f * image.getExtentZ());
//        cornerNode = new Node();
//        cornerNode.setParent(this);
//        cornerNode.setLocalPosition(localPosition);
//        cornerNode.setRenderable(ulCorner.getNow(null));


//        test position
//        localPosition.set(-0.0f, 0.0f, 0.0f );
        localPosition.set(-0.0f * image.getExtentX(), 0.0f , 0.0f * image.getExtentZ());
        //TODO:Vérifie si ça marche
        cornerNode = new Node();
        cornerNode.setParent(this);
        cornerNode.setLocalPosition(localPosition);
        Quaternion quaternion = new Quaternion(0.0f, 0.0f * image.getExtentX(), 0.0f, 0.0f* image.getExtentZ());
//        cornerNode.setLocalRotation(quaternion);
        cornerNode.setWorldRotation(quaternion);
        cornerNode.setRenderable(test.getNow(null));

//        cornerNode.setOnTapListener((hitTestResult, motionEvent) -> {
//            //TODO: turn to another activity: info_activity
//            Intent intent = new Intent();
//            Toast.makeText(this.nodeContext, "testInfoButtonClick", Toast.LENGTH_SHORT).show();
//
//        });
    }


    //2018.
    @Override
    public void onTap(HitTestResult hitTestResult, MotionEvent motionEvent) {
        if (cornerNode == null) {
            return;
        }

        cornerNode.setEnabled(cornerNode.isEnabled());
    }

    public boolean onTouch(HitTestResult hitTestResult, MotionEvent motionEvent){
        if (cornerNode == null) {
            return false;
        }


        cornerNode.setEnabled(cornerNode.isEnabled());
        return true;
    }

    public Context getNodeContext(){
        return this.nodeContext;
    }
}
